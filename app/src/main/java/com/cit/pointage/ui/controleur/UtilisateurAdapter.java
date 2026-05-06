package com.cit.pointage.ui.controleur;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cit.pointage.R;
import com.cit.pointage.model.response.UtilisateurResponse;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurAdapter extends RecyclerView
        .Adapter<UtilisateurAdapter.ViewHolder> {

    private List<UtilisateurResponse> utilisateurs =
            new ArrayList<>();
    private OnUtilisateurClickListener listener;

    public interface OnUtilisateurClickListener {
        void onUtilisateurClick(UtilisateurResponse u);
    }

    public UtilisateurAdapter(
            OnUtilisateurClickListener listener) {
        this.listener = listener;
    }

    public void setUtilisateurs(
            List<UtilisateurResponse> liste) {
        this.utilisateurs = liste;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                        parent.getContext())
                .inflate(R.layout.item_utilisateur,
                        parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder, int position) {
        UtilisateurResponse u =
                utilisateurs.get(position);
        holder.bind(u, listener);
    }

    @Override
    public int getItemCount() {
        return utilisateurs.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivAvatar;
        TextView tvNomComplet;
        TextView tvMatricule;
        TextView tvCategorie;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(
                    R.id.ivAvatar);
            tvNomComplet = itemView.findViewById(
                    R.id.tvNomComplet);
            tvMatricule = itemView.findViewById(
                    R.id.tvMatricule);
            tvCategorie = itemView.findViewById(
                    R.id.tvCategorie);
        }

        void bind(UtilisateurResponse u,
                  OnUtilisateurClickListener listener) {

            tvNomComplet.setText(u.getNomComplet());
            tvMatricule.setText(u.getMatricule());
            tvCategorie.setText(
                    formaterCategorie(u.getCategorie()));

            if (u.getPhotoUrl() != null
                    && !u.getPhotoUrl().isEmpty()) {
                Glide.with(itemView.getContext())
                        .load(u.getPhotoUrl())
                        .circleCrop()
                        .placeholder(R.color.olive_100)
                        .into(ivAvatar);
            }

            itemView.setOnClickListener(
                    v -> listener.onUtilisateurClick(u));
        }

        private String formaterCategorie(String cat) {
            if (cat == null) return "";
            switch (cat) {
                case "ELEVE":
                    return "Élève";
                case "OFFICIER_STAGIAIRE":
                    return "Officier";
                case "PERSONNEL_ADMIN":
                    return "Admin";
                case "PERSONNEL_ENCADRANT":
                    return "Encadrant";
                default:
                    return cat;
            }
        }
    }
}