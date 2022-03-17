package com.reitech.gym.ui.exerciselist;

import android.view.View;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.reitech.gym.R;
import com.reitech.gym.ui.tracker.Workout;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;

class ExerciseSection extends Section {
    private final String title;
    private final List<String> list;
    private final ClickListener clickListener;

    private boolean expanded = true;

    ExerciseSection(@NonNull final String title, @NonNull final List<String> list,
                    @NonNull final ClickListener clickListener) {
        // call constructor with layout resources for this Section header and items
        super(SectionParameters.builder()
                .itemResourceId(R.layout.exercise_list_item)
                .headerResourceId(R.layout.exercise_list_header)
                .build());

        this.title = title;
        this.list = list;
        this.clickListener = clickListener;
    }

    @Override
    public int getContentItemsTotal() {
        return expanded ? list.size() : 0;
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        // return a custom instance of ViewHolder for the items of this section
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemHolder = (ItemViewHolder) holder;


        // bind your view here
        itemHolder.tvItem.setText(list.get(position));
        itemHolder.rootView.setOnClickListener(v -> clickListener.onItemRootViewClicked(this, position, itemHolder.tvItem.getText().toString()));

    }



    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        // return an empty instance of ViewHolder for the headers of this section
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(final RecyclerView.ViewHolder holder) {
        final HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

        headerHolder.tvTitle.setText(title);
        headerHolder.imgArrow.setImageResource(
                expanded ? R.drawable.ic_baseline_arrow_drop_up_white : R.drawable.ic_baseline_arrow_drop_down_white
        );

        headerHolder.rootView.setOnClickListener(v -> clickListener.onHeaderRootViewClicked(this));
    }

    boolean isExpanded() {
        return expanded;
    }

    void setExpanded(final boolean expanded) {
        this.expanded = expanded;
    }

    interface ClickListener {
        void onHeaderRootViewClicked(@NonNull final ExerciseSection section);

        void onItemRootViewClicked(@NonNull final ExerciseSection section, final int itemAdapterPosition,
                                   final String exerciseName);
    }
}
