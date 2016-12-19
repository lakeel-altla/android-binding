package com.lakeel.altla.vision.builder.presentation.presenter;

import com.lakeel.altla.android.log.Log;
import com.lakeel.altla.android.log.LogFactory;
import com.lakeel.altla.vision.builder.R;
import com.lakeel.altla.vision.builder.presentation.model.AreaDescriptionModel;
import com.lakeel.altla.vision.builder.presentation.view.AreaDescriptionListItemView;
import com.lakeel.altla.vision.builder.presentation.view.AreaDescriptionListView;
import com.lakeel.altla.vision.domain.usecase.DeleteUserAreaDescriptionUseCase;
import com.lakeel.altla.vision.domain.usecase.FindAllAreaDescriptionUseCase;
import com.lakeel.altla.vision.domain.usecase.GetAreaDescriptionCacheDirectoryUseCase;
import com.lakeel.altla.vision.domain.usecase.SaveUserAreaDescriptionUseCase;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

public final class AreaDescriptionListPresenter {

    private static final Log LOG = LogFactory.getLog(AreaDescriptionListPresenter.class);

    @Inject
    FindAllAreaDescriptionUseCase findAllAreaDescriptionUseCase;

    @Inject
    GetAreaDescriptionCacheDirectoryUseCase getAreaDescriptionCacheDirectoryUseCase;

    @Inject
    SaveUserAreaDescriptionUseCase saveUserAreaDescriptionUseCase;

    @Inject
    DeleteUserAreaDescriptionUseCase deleteUserAreaDescriptionUseCase;

    private final CompositeSubscription compositeSubscription = new CompositeSubscription();

    private final List<AreaDescriptionModel> models = new ArrayList<>();

    private AreaDescriptionListView view;

    private String exportingId;

    private int exportingPosition;

    private long prevBytesTransferred;

    @Inject
    public AreaDescriptionListPresenter() {
    }

    public void onCreateView(@NonNull AreaDescriptionListView view) {
        this.view = view;
    }

    public void onStart() {
        models.clear();

        LOG.d("Loading all area descriptions...");

        Subscription subscription = findAllAreaDescriptionUseCase
                .execute()
                // Map it to the model for the view.
                .map(userAreaDescription -> {
                    AreaDescriptionModel model =
                            new AreaDescriptionModel(userAreaDescription.areaDescriptionId, userAreaDescription.name);
                    model.synced = userAreaDescription.synced;
                    return model;
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(models -> {
                    LOG.d("Loaded all area descriptions: count = %d", models.size());

                    this.models.addAll(models);
                    view.updateItems();
                }, e -> {
                    LOG.e("Failed to load all area descriptions.", e);

                    // TODO: error snackbar
                });
        compositeSubscription.add(subscription);
    }

    public void onStop() {
        compositeSubscription.clear();
    }

    public void onCreateItemView(@NonNull AreaDescriptionListItemView itemView) {
        AreaDescriptionListPresenter.ItemPresenter itemPresenter = new AreaDescriptionListPresenter.ItemPresenter();
        itemPresenter.onCreateItemView(itemView);
        itemView.setItemPresenter(itemPresenter);
    }

    public int getItemCount() {
        return models.size();
    }

    public void onExported() {
        if (exportingId == null) {
            throw new IllegalStateException("exportingId == null");
        }

        LOG.d("Syncing the area description: id = %s", exportingId);

        prevBytesTransferred = 0;

        Subscription subscription = saveUserAreaDescriptionUseCase
                .execute(exportingId, (totalBytes, bytesTransferred) -> {
                    long increment = bytesTransferred - prevBytesTransferred;
                    prevBytesTransferred = bytesTransferred;
                    view.setUploadProgressDialogProgress(totalBytes, increment);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> view.showUploadProgressDialog())
                .doOnUnsubscribe(() -> view.hideUploadProgressDialog())
                .subscribe(userAreaDescription -> {
                    LOG.d("Synced the area description.");

                    // Mark as synced.
                    AreaDescriptionModel model = models.get(exportingPosition);
                    model.synced = true;

                    view.showSnackbar(R.string.snackbar_done);
                    view.updateItem(exportingPosition);
                }, e -> {
                    LOG.e(String.format("Failed to sync the area description: id = %s", exportingId), e);
                    view.showSnackbar(R.string.snackbar_failed);
                });
        compositeSubscription.add(subscription);
    }

    public final class ItemPresenter {

        private AreaDescriptionListItemView itemView;

        private void onCreateItemView(@NonNull AreaDescriptionListItemView itemView) {
            this.itemView = itemView;
        }

        public void onBind(int position) {
            AreaDescriptionModel model = models.get(position);
            itemView.showModel(model);
        }

        public void onClickImageButtonSyncCloud(int position) {
            AreaDescriptionModel model = models.get(position);
            if (model.synced) {
                desync(position, model);
            } else {
                sync(position, model);
            }
        }

        private void desync(int position, AreaDescriptionModel model) {
            LOG.d("Desyncing the area description: id = %s", model.id);

            Subscription subscription = deleteUserAreaDescriptionUseCase
                    .execute(model.id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(() -> view.showDesyncProgressDialog())
                    .doOnUnsubscribe(() -> view.hideDesyncProgressDialog())
                    .subscribe(s -> {
                        LOG.d("Desynced the area description.");

                        model.synced = false;

                        view.updateItem(position);
                        view.showSnackbar(R.string.snackbar_done);
                    }, e -> {
                        LOG.e("Failed to desync the area description.", e);

                        view.showSnackbar(R.string.snackbar_failed);
                    });
            compositeSubscription.add(subscription);
        }

        private void sync(int position, AreaDescriptionModel model) {
            Subscription subscription = getAreaDescriptionCacheDirectoryUseCase
                    .execute()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(directory -> {
                        exportingId = model.id;
                        exportingPosition = position;
                        view.showExportActivity(exportingId, directory);
                    });
            compositeSubscription.add(subscription);
        }
    }
}
