package com.cit.pointage.ui.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cit.pointage.R;
import com.cit.pointage.model.response.PermissionResponse;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

/**
 * PermissionAdapter — RecyclerView Adapter pour la liste des permissions en cours.
 * Le bouton "Annuler" déclenche le callback OnAnnulerListener.
 */
public class PermissionAdapter extends
        RecyclerView.Adapter<PermissionAdapter.PermissionViewHolder> {

    public interface OnAnnulerListener {
        void onAnnuler(PermissionResponse permission);
    }

    private List<PermissionResponse> permissions = new ArrayList<>();
    private final OnAnnulerListener listener;

    public PermissionAdapter(OnAnnulerListener listener) {
        this.listener = listener;
    }

    public void setPermissions(List<PermissionResponse> permissions) {
        this.permissions = permissions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PermissionViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_permission, parent, false);
        return new PermissionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull PermissionViewHolder holder, int position) {
        holder.bind(permissions.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return permissions.size();
    }

    // ════════ ViewHolder ════════

    static class PermissionViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvNomUtilisateur;
        private final TextView tvMatricule;
        private final TextView tvStatut;
        private final TextView tvDateDebut;
        private final TextView tvDateFin;
        private final MaterialButton btnAnnuler;

        PermissionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomUtilisateur = itemView.findViewById(R.id.tvNomUtilisateur);
            tvMatricule      = itemView.findViewById(R.id.tvMatricule);
            tvStatut         = itemView.findViewById(R.id.tvStatut);
            tvDateDebut      = itemView.findViewById(R.id.tvDateDebut);
            tvDateFin        = itemView.findViewById(R.id.tvDateFin);
            btnAnnuler       = itemView.findViewById(R.id.btnAnnuler);
        }

        void bind(PermissionResponse permission, OnAnnulerListener listener) {
            tvNomUtilisateur.setText(permission.getNomCompletUtilisateur());
            tvMatricule.setText(permission.getMatricule());
            tvDateDebut.setText(permission.getDateDebut() != null
                    ? permission.getDateDebut() : "—");
            tvDateFin.setText(permission.getDateFin() != null
                    ? permission.getDateFin() : "—");

            // Statut coloré
            if (permission.isValide()) {
                tvStatut.setText("En cours");
            } else if (permission.isExpiree()) {
                tvStatut.setText("Expirée");
            } else {
                tvStatut.setText(permission.getStatut() != null
                        ? permission.getStatut() : "—");
            }

            // Bouton annuler
            btnAnnuler.setOnClickListener(v -> listener.onAnnuler(permission));
        }
    }
}
