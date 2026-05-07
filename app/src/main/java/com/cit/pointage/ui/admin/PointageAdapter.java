package com.cit.pointage.ui.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cit.pointage.R;
import com.cit.pointage.model.response.PointageResponse;

import java.util.ArrayList;
import java.util.List;

public class PointageAdapter extends RecyclerView.Adapter<PointageAdapter.ViewHolder> {

    private List<PointageResponse> pointages = new ArrayList<>();

    public void setPointages(List<PointageResponse> liste) {
        this.pointages = liste;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pointage, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(pointages.get(position));
    }

    @Override
    public int getItemCount() {
        return pointages.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvInitiales;
        TextView tvNomUtilisateur;
        TextView tvMatricule;
        TextView tvTypeScan;
        TextView tvHeure;
        TextView tvBadgeRetard;
        ImageView ivHorsLigne;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvInitiales = itemView.findViewById(R.id.tvInitiales);
            tvNomUtilisateur = itemView.findViewById(R.id.tvNomUtilisateur);
            tvMatricule = itemView.findViewById(R.id.tvMatricule);
            tvTypeScan = itemView.findViewById(R.id.tvTypeScan);
            tvHeure = itemView.findViewById(R.id.tvHeure);
            tvBadgeRetard = itemView.findViewById(R.id.tvBadgeRetard);
            ivHorsLigne = itemView.findViewById(R.id.ivHorsLigne);
        }

        void bind(PointageResponse pointage) {
            tvNomUtilisateur.setText(pointage.getNomCompletUtilisateur());
            tvMatricule.setText(pointage.getMatricule());

            // Initiales (2 premières lettres du nom ou prénom)
            String nom = pointage.getNomCompletUtilisateur();
            if (nom != null && nom.length() >= 2) {
                tvInitiales.setText(nom.substring(0, 2).toUpperCase());
            } else {
                tvInitiales.setText("??");
            }

            // Heure
            String dateHeure = pointage.getDateHeure();
            if (dateHeure != null && dateHeure.length() >= 16) {
                // Extrait juste l'heure HH:mm
                tvHeure.setText(dateHeure.substring(11, 16));
            } else {
                tvHeure.setText("--:--");
            }

            // Type et Méthode
            String typeFormatted = formatType(pointage.getType());
            String methodeFormatted = formatMethode(pointage.getMethode());
            tvTypeScan.setText(typeFormatted + " • " + methodeFormatted);

            // Badge Retard
            if (pointage.isEstRetard()) {
                tvBadgeRetard.setVisibility(View.VISIBLE);
            } else {
                tvBadgeRetard.setVisibility(View.GONE);
            }

            // Indicateur Hors ligne
            if (pointage.isHorsLigne()) {
                ivHorsLigne.setVisibility(View.VISIBLE);
            } else {
                ivHorsLigne.setVisibility(View.GONE);
            }
        }

        private String formatType(String type) {
            if (type == null) return "INCONNU";
            switch (type) {
                case "ENTREE": return "Entrée";
                case "SORTIE": return "Sortie";
                case "DEPART_PERMISSION": return "Départ Perm.";
                case "RETOUR_PERMISSION": return "Retour Perm.";
                default: return type;
            }
        }

        private String formatMethode(String methode) {
            if (methode == null) return "INCONNU";
            if (methode.equals("QR_SCAN")) return "Scan QR";
            if (methode.equals("MANUELLE")) return "Manuelle";
            return methode;
        }
    }
}
