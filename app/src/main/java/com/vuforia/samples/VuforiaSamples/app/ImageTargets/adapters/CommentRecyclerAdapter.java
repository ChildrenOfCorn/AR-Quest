package com.vuforia.samples.VuforiaSamples.app.ImageTargets.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.vuforia.samples.VuforiaSamples.R;
import com.vuforia.samples.ar.data.beans.Comment;
import com.vuforia.samples.ar.data.beans.UserInfo;

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

    public void addComment(Comment comment) {
        this.comments.add(comment);
        notifyDataSetChanged();
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_comment_item, parent, false));
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
        ImageView userIcon;
        TextView userName;
        TextView userComment;
        RatingBar userRating;

        CommentViewHolder(View itemView) {
            super(itemView);
            userIcon = (ImageView) itemView.findViewById(R.id.userIcon);
            userName = (TextView) itemView.findViewById(R.id.userName);
            userComment = (TextView) itemView.findViewById(R.id.userComment);
            userRating = (RatingBar) itemView.findViewById(R.id.userRating);
        }

        void bind(Comment comment) {
            UserInfo owner = comment.getOwner();
            userName.setText(owner != null ? owner.getName() : "");

            userComment.setText(comment.getText());

            userRating.setRating(comment.getRating());
        }
    }
}
