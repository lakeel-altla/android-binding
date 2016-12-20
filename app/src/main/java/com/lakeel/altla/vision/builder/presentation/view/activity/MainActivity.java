package com.lakeel.altla.vision.builder.presentation.view.activity;

import com.google.atap.tango.ux.TangoUx;
import com.google.atap.tangoservice.Tango;
import com.google.atap.tangoservice.TangoConfig;
import com.google.atap.tangoservice.TangoCoordinateFramePair;
import com.google.atap.tangoservice.TangoPoseData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.lakeel.altla.android.log.Log;
import com.lakeel.altla.android.log.LogFactory;
import com.lakeel.altla.tango.TangoUpdateDispatcher;
import com.lakeel.altla.vision.builder.R;
import com.lakeel.altla.vision.builder.presentation.app.MyApplication;
import com.lakeel.altla.vision.builder.presentation.di.ActivityScopeContext;
import com.lakeel.altla.vision.builder.presentation.di.component.ActivityComponent;
import com.lakeel.altla.vision.builder.presentation.di.module.ActivityModule;
import com.lakeel.altla.vision.builder.presentation.view.NavigationViewHost;
import com.lakeel.altla.vision.builder.presentation.view.fragment.AreaDescriptionListFragment;
import com.lakeel.altla.vision.builder.presentation.view.fragment.MainFragment;
import com.lakeel.altla.vision.builder.presentation.view.fragment.RegisterTextureFragment;
import com.lakeel.altla.vision.builder.presentation.view.fragment.SignInFragment;
import com.lakeel.altla.vision.builder.presentation.view.fragment.TangoPermissionFragment;
import com.lakeel.altla.vision.domain.usecase.ObserveConnectionUseCase;
import com.lakeel.altla.vision.domain.usecase.ObserveUserProfileUseCase;
import com.lakeel.altla.vision.domain.usecase.SignOutUseCase;
import com.projecttango.tangosupport.TangoSupport;
import com.squareup.picasso.Picasso;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

public final class MainActivity extends AppCompatActivity
        implements ActivityScopeContext,
                   NavigationViewHost,
                   SignInFragment.OnShowMainFragmentListener,
                   TangoPermissionFragment.InteractionListener,
                   MainFragment.InteractionListener,
                   RegisterTextureFragment.InteractionListener,
                   NavigationView.OnNavigationItemSelectedListener {

    private static final Log LOG = LogFactory.getLog(MainActivity.class);

    private static final List<TangoCoordinateFramePair> FRAME_PAIRS;

    @Inject
    Tango tango;

    @Inject
    TangoUx tangoUx;

    @Inject
    TangoUpdateDispatcher tangoUpdateDispatcher;

    @Inject
    TangoConfig tangoConfig;

    @Inject
    ObserveUserProfileUseCase observeUserProfileUseCase;

    @Inject
    ObserveConnectionUseCase observeConnectionUseCase;

    @Inject
    SignOutUseCase signOutUseCase;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.navigation_view)
    NavigationView navigationView;

    private final CompositeSubscription compositeSubscription = new CompositeSubscription();

    private ActivityComponent activityComponent;

    private NavigationViewHeader navigationViewHeader;

    private MaterialMenuDrawable materialMenu;

    private Subscription subscriptionObserveUserProfile;

    private Subscription subscriptionObserveConnection;

    static {
        FRAME_PAIRS = new ArrayList<>();
        FRAME_PAIRS.add(new TangoCoordinateFramePair(TangoPoseData.COORDINATE_FRAME_START_OF_SERVICE,
                                                     TangoPoseData.COORDINATE_FRAME_DEVICE));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // NOTE:
        //
        // Any injection must be done before super.onCreate()
        // because fragments are already attached to an activity when they are resumed or instant-run.
        activityComponent = MyApplication.getApplicationComponent(this)
                                         .activityComponent(new ActivityModule(this));
        activityComponent.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Enable the toolbar and the material menu.
        setSupportActionBar(toolbar);
        materialMenu = new MaterialMenuDrawable(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN);
        toolbar.setNavigationIcon(materialMenu);

        // Using Material Menu, Construct without the toolbar and do not call ActionBarDrawerToggle#syncState().
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationViewHeader = new NavigationViewHeader(navigationView);

        showSignInFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(navigationViewHeader);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Unsubscribe the connection.
        if (subscriptionObserveConnection != null) {
            subscriptionObserveConnection.unsubscribe();
            subscriptionObserveConnection = null;
        }

        // Unsubscribe the user profile.
        if (subscriptionObserveUserProfile != null) {
            subscriptionObserveUserProfile.unsubscribe();
            subscriptionObserveUserProfile = null;
        }

        compositeSubscription.clear();

        FirebaseAuth.getInstance().removeAuthStateListener(navigationViewHeader);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // NOTE:
        //
        // 現状、TangoUX を用いるとデバッグ モードでは起動しなくなる。
        // これは、TangoUxLayout の配置の有無ではなく、TangoUx#start の実行により発生する。
        // このため、開発効率のために TangoUX を OFF にする場合には、TangoUx#start も止めなければならない。
//        mTangoUx.start(new TangoUx.StartParams());

        tango.connectListener(FRAME_PAIRS, tangoUpdateDispatcher);
        tango.connect(tangoConfig);

        TangoSupport.initialize();
    }

    @Override
    protected void onPause() {
        super.onPause();

        tango.disconnect();
        tangoUx.stop();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_scene_builder:
                onShowMainFragment();
                break;
            case R.id.nav_area_description_list:
                onShowAreaDescriptionListFragment();
                break;
            case R.id.nav_sign_out:
                onSignOut();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    @Override
    public void onShowTangoPermissionFragment() {
        showTangoPermissionFragment();
    }

    @Override
    public void onShowMainFragment() {
        showMainFragment();
    }

    @Override
    public void onShowEditTextureFragment(@Nullable String id) {
        RegisterTextureFragment fragment = RegisterTextureFragment.newInstance(id);
        getSupportFragmentManager().beginTransaction()
                                   .addToBackStack(null)
                                   .replace(R.id.fragment_container, fragment)
//                                   .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                   .commit();
    }

    @Override
    public void animateHomeIconToBurger() {
        LOG.d("animateHomeIconToBurger()");
        materialMenu.animateIconState(MaterialMenuDrawable.IconState.BURGER);
    }

    @Override
    public void animateHomeIconToArrow() {
        LOG.d("animateHomeIconToArrow()");
        materialMenu.animateIconState(MaterialMenuDrawable.IconState.ARROW);
    }

    @Override
    public void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    private void onShowAreaDescriptionListFragment() {
        showAreaDescriptionListFragment();
    }

    private void onSignOut() {
        Subscription subscription = signOutUseCase.execute()
                                                  .observeOn(AndroidSchedulers.mainThread())
                                                  .subscribe();
        compositeSubscription.add(subscription);

        showSignInFragment();
    }

    private void showSignInFragment() {
        materialMenu.setVisible(false);

        SignInFragment fragment = SignInFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                                   .replace(R.id.fragment_container, fragment)
                                   .commit();
    }

    private void showTangoPermissionFragment() {
        materialMenu.setVisible(false);

        TangoPermissionFragment fragment = TangoPermissionFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                                   .replace(R.id.fragment_container, fragment)
                                   .commit();
    }

    private void showMainFragment() {
        materialMenu.setVisible(true);

        MainFragment fragment = MainFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                                   .replace(R.id.fragment_container, fragment)
                                   .commit();
    }

    private void showAreaDescriptionListFragment() {
        AreaDescriptionListFragment fragment = AreaDescriptionListFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                                   .replace(R.id.fragment_container, fragment)
                                   .commit();
    }

    class NavigationViewHeader implements FirebaseAuth.AuthStateListener {

        @BindView(R.id.image_view_user_photo)
        ImageView imageViewUserPhoto;

        @BindView(R.id.text_view_user_name)
        TextView textViewUserName;

        @BindView(R.id.text_view_user_email)
        TextView textViewUserEmail;

        private NavigationViewHeader(@NonNull NavigationView navigationView) {
            ButterKnife.bind(this, navigationView.getHeaderView(0));
        }

        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();

            if (user != null) {
                // Subscribe the connection.
                if (subscriptionObserveConnection == null) {
                    subscriptionObserveConnection = observeConnectionUseCase
                            .execute()
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe();
                }

                // Subscribe the user profile.
                if (subscriptionObserveUserProfile == null) {
                    subscriptionObserveUserProfile = observeUserProfileUseCase
                            .execute()
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(userProfile -> {
                                // Update UI each time the user profile is updated.
                                if (userProfile.photoUri != null) {
                                    Uri photoUri = Uri.parse(userProfile.photoUri);
                                    Picasso.with(MainActivity.this).load(photoUri).into(imageViewUserPhoto);
                                }
                                textViewUserName.setText(userProfile.displayName);
                                textViewUserEmail.setText(userProfile.email);
                            });
                }
            } else {
                // Unsubscribe the connection.
                if (subscriptionObserveConnection != null) {
                    subscriptionObserveConnection.unsubscribe();
                    subscriptionObserveConnection = null;
                }

                // Unsubscribe the user profile.
                if (subscriptionObserveUserProfile != null) {
                    subscriptionObserveUserProfile.unsubscribe();
                    subscriptionObserveUserProfile = null;
                }

                // Clear UI.
                imageViewUserPhoto.setImageBitmap(null);
                textViewUserName.setText(null);
                textViewUserEmail.setText(null);
            }
        }
    }
}
