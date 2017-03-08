package com.lakeel.altla.vision.data.repository.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.lakeel.altla.vision.domain.helper.ObservableData;
import com.lakeel.altla.vision.domain.helper.ObservableDataList;
import com.lakeel.altla.vision.domain.helper.OnFailureListener;
import com.lakeel.altla.vision.domain.helper.OnSuccessListener;
import com.lakeel.altla.vision.domain.model.Scene;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public final class UserSceneRepository extends BaseDatabaseRepository {

    private static final String BASE_PATH = "userScenes";

    private static final String FIELD_NAME = "name";

    private static final String FIELD_AREA_ID = "areaId";

    public UserSceneRepository(@NonNull FirebaseDatabase database) {
        super(database);
    }

    public void save(@NonNull Scene scene) {
        if (scene.getUserId() == null) throw new IllegalArgumentException("scene.getUserId() must be not null.");

        scene.setUpdatedAtAsLong(-1);

        getDatabase().getReference()
                     .child(BASE_PATH)
                     .child(scene.getUserId())
                     .child(scene.getId())
                     .setValue(scene, (error, reference) -> {
                         if (error != null) {
                             getLog().e(String.format("Failed to save: reference = %s", reference),
                                        error.toException());
                         }
                     });
    }

    public void find(@NonNull String userId, @NonNull String sceneId, OnSuccessListener<Scene> onSuccessListener,
                     OnFailureListener onFailureListener) {
        getDatabase().getReference()
                     .child(BASE_PATH)
                     .child(userId)
                     .child(sceneId)
                     .addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(DataSnapshot snapshot) {
                             Scene scene = snapshot.getValue(Scene.class);
                             if (onSuccessListener != null) onSuccessListener.onSuccess(scene);
                         }

                         @Override
                         public void onCancelled(DatabaseError error) {
                             if (onFailureListener != null) onFailureListener.onFailure(error.toException());
                         }
                     });
    }

    public void findAll(@NonNull String userId, OnSuccessListener<List<Scene>> onSuccessListener,
                        OnFailureListener onFailureListener) {
        getDatabase().getReference()
                     .child(BASE_PATH)
                     .child(userId)
                     .orderByChild(FIELD_NAME)
                     .addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(DataSnapshot snapshot) {
                             List<Scene> list = new ArrayList<>((int) snapshot.getChildrenCount());
                             for (DataSnapshot child : snapshot.getChildren()) {
                                 list.add(child.getValue(Scene.class));
                             }
                             if (onSuccessListener != null) onSuccessListener.onSuccess(list);
                         }

                         @Override
                         public void onCancelled(DatabaseError error) {
                             if (onFailureListener != null) onFailureListener.onFailure(error.toException());
                         }
                     });
    }

    public void findByAreaId(@NonNull String userId, @NonNull String areaId,
                             OnSuccessListener<List<Scene>> onSuccessListener,
                             OnFailureListener onFailureListener) {
        getDatabase().getReference()
                     .child(BASE_PATH)
                     .child(userId)
                     .orderByChild(FIELD_AREA_ID)
                     .equalTo(areaId)
                     .addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(DataSnapshot snapshot) {
                             List<Scene> list = new ArrayList<>((int) snapshot.getChildrenCount());
                             for (DataSnapshot child : snapshot.getChildren()) {
                                 list.add(child.getValue(Scene.class));
                             }
                             if (onSuccessListener != null) onSuccessListener.onSuccess(list);
                         }

                         @Override
                         public void onCancelled(DatabaseError error) {
                             if (onFailureListener != null) onFailureListener.onFailure(error.toException());
                         }
                     });
    }

    @NonNull
    public ObservableData<Scene> observe(@NonNull String userId, @NonNull String sceneId) {
        DatabaseReference reference = getDatabase().getReference()
                                                   .child(BASE_PATH)
                                                   .child(userId)
                                                   .child(sceneId);

        return new ObservableData<>(reference, snapshot -> snapshot.getValue(Scene.class));
    }

    @NonNull
    public ObservableDataList<Scene> observeAll(@NonNull String userId) {
        Query query = getDatabase().getReference()
                                   .child(BASE_PATH)
                                   .child(userId)
                                   .orderByChild(FIELD_NAME);

        return new ObservableDataList<>(query, snapshot -> snapshot.getValue(Scene.class));
    }

    @NonNull
    public ObservableDataList<Scene> observeByAreaId(@NonNull String userId, @NonNull String areaId) {
        Query query = getDatabase().getReference()
                                   .child(BASE_PATH)
                                   .child(userId)
                                   .orderByChild(FIELD_AREA_ID)
                                   .equalTo(areaId);

        return new ObservableDataList<>(query, snapshot -> snapshot.getValue(Scene.class));
    }

    public void delete(@NonNull String userId, @NonNull String sceneId) {
        getDatabase().getReference()
                     .child(BASE_PATH)
                     .child(userId)
                     .child(sceneId)
                     .removeValue((error, reference) -> {
                         if (error != null) {
                             getLog().e(String.format("Failed to remove: reference = %s", reference),
                                        error.toException());
                         }
                     });
    }
}
