package com.example.restclientapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import com.example.restclientapp.model.Member;

import java.util.ArrayList;
import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {

    // CAMBIO 1: La lista ahora es de Member
    private List<Member> members = new ArrayList<>();

    public void setMembers(List<Member> members) {
        this.members = members;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // CAMBIO 3: Obtenemos un objeto Member
        Member m = members.get(position);

        // Usamos los getters de Member (getName, getPoints)
        holder.tvName.setText(m.getName());
        holder.tvPoints.setText(m.getPoints() + " Puntos");

        Picasso.get()
                .load(m.getAvatar())
                .into(holder.imgAvatar);
    }

    @Override
    public int getItemCount() { return members.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPoints;
        ImageView imgAvatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvMemberName);
            tvPoints = itemView.findViewById(R.id.tvMemberPoints);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
        }
    }
}