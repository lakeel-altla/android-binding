package com.lakeel.altla.vision.builder.presentation.view.adapter;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lakeel.altla.vision.builder.R;
import com.lakeel.altla.vision.builder.presentation.model.TextureItemModel;
import com.lakeel.altla.vision.builder.presentation.presenter.MainPresenter;
import com.lakeel.altla.vision.builder.presentation.view.TextureItemView;

import android.content.ClipData;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public final class TextureListAdapter extends RecyclerView.Adapter<TextureListAdapter.ViewHolderTexture> {

    private static final ClipData CLIP_DATA_DUMMY = ClipData.newPlainText("", "");

    private final MainPresenter presenter;

    public TextureListAdapter(@NonNull MainPresenter presenter) {
        this.presenter = presenter;
    }

    private LayoutInflater inflater;

    @Override
    public final ViewHolderTexture onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }

        View itemView = inflater.inflate(R.layout.item_texture, parent, false);
        return new ViewHolderTexture(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolderTexture holder, int position) {
        holder.itemPresenter.onBind(position);
    }

    @Override
    public void onViewRecycled(ViewHolderTexture holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getTextureItemCount();
    }

    public final class ViewHolderTexture extends RecyclerView.ViewHolder implements TextureItemView {

        @BindView(R.id.view_top)
        View viewTop;

        @BindView(R.id.image_view_texture)
        ImageView imageViewTexture;

        @BindView(R.id.progress_bar_load_texture)
        ProgressBar progressBarLoadTexture;

        @BindView(R.id.view_group_texture_detail)
        ViewGroup viewGroupTextureDetail;

        @BindView(R.id.text_view_texture_name)
        TextView textViewTextureName;

        @BindView(R.id.image_button_delete_texture)
        ImageButton imageButtonDeleteTexture;

        private final View.DragShadowBuilder dragShadowBuilder;

        private final MainPresenter.TextureItemPresenter itemPresenter;

        private MaterialDialog materialDialog;

        private ViewHolderTexture(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemPresenter = presenter.createTextureItemPresenter();
            itemPresenter.onCreateItemView(this);

            dragShadowBuilder = new View.DragShadowBuilder(imageViewTexture);

            imageViewTexture.setOnDragListener((view, dragEvent) -> {
                switch (dragEvent.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        // returns true to accept a drag event.
                        return true;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        return true;
                    case DragEvent.ACTION_DRAG_EXITED:
                        return true;
                    case DragEvent.ACTION_DROP:
                        // does not accept to drop here.
                        return false;
                    case DragEvent.ACTION_DRAG_ENDED:
                        return true;
                }

                return false;
            });

            // Hide.
            imageViewTexture.setVisibility(View.GONE);
            progressBarLoadTexture.setVisibility(View.GONE);
            viewGroupTextureDetail.setVisibility(View.GONE);
        }

        @Override
        public void onModelUpdated(@NonNull TextureItemModel model) {
            textViewTextureName.setText(model.name);

            if (model.bitmap == null) {
                // Load the bitmap.
                imageViewTexture.setVisibility(View.GONE);
                progressBarLoadTexture.setVisibility(View.VISIBLE);
                itemPresenter.onLoadBitmap(getAdapterPosition());
            } else {
                // Show the bitmap and hide the progress bar.
                imageViewTexture.setImageBitmap(model.bitmap);
                imageViewTexture.setVisibility(View.VISIBLE);
                progressBarLoadTexture.setVisibility(View.GONE);
            }
        }

        @Override
        public void onShowProgress(int max, int progress) {
            progressBarLoadTexture.setMax(max);
            progressBarLoadTexture.setProgress(progress);
        }

        @Override
        public void onHideProgress() {
            progressBarLoadTexture.setVisibility(View.GONE);
        }

        @Override
        public void onStartDrag() {
            imageViewTexture.startDrag(CLIP_DATA_DUMMY, dragShadowBuilder, null, 0);
        }

        @Override
        public void onSelect(int selectedPosition, boolean selected) {
            if (selectedPosition == getAdapterPosition()) {
                viewTop.setSelected(selected);

                if (selected) {
                    viewGroupTextureDetail.setVisibility(View.VISIBLE);
                } else {
                    viewGroupTextureDetail.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public void onShowDeleteTextureConfirmationDialog() {
            if (materialDialog != null && materialDialog.isShowing()) {
                // Skip to protect against double taps.
                return;
            }

            if (materialDialog == null) {
                materialDialog = new MaterialDialog.Builder(itemView.getContext())
                        .content(R.string.dialog_content_confirm_delete_texture)
                        .positiveText(R.string.dialog_ok)
                        .negativeText(R.string.dialog_cancel)
                        .onPositive((dialog, which) -> itemPresenter.onDelete(getAdapterPosition()))
                        .build();
            }

            materialDialog.show();
        }

        @OnClick(R.id.view_top)
        void onClickViewTop() {
            itemPresenter.onClickViewTop(getAdapterPosition());
        }

        @OnLongClick(R.id.view_top)
        boolean onLongClickViewTop() {
            itemPresenter.onLongClickViewTop(getAdapterPosition());
            return true;
        }

        @OnClick(R.id.image_button_edit_texture)
        void onClickImageButtonEditTexture() {
            itemPresenter.onClickImageButtonEditTexture(getAdapterPosition());
        }

        @OnClick(R.id.image_button_delete_texture)
        void onClickImageButtonDeleteTexture() {
            itemPresenter.onClickImageButtonDeleteTexture(getAdapterPosition());
        }
    }
}
