package com.vuforia.samples.VuforiaSamples.app.ImageTargets.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vuforia.samples.VuforiaSamples.R;
import com.vuforia.samples.ar.data.beans.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grishberg on 16.04.17.
 */

public class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentRecyclerAdapter.CommentViewHolder> {

    public static final int INITIAL_CAPACITY = 30;
    private final ArrayList<Comment> comments = new ArrayList<>(INITIAL_CAPACITY);

    public void setComments(List<Comment> comments) {
        this.comments.clear();
        this.comments.addAll(comments);
        notifyDataSetChanged();
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_comment_item, parent, false));
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.bind(comment);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        //TODO: вставь лэйауты тут
        CommentViewHolder(View itemView) {
            super(itemView);
        }

        void bind(Comment comment) {

        }
    }
}
