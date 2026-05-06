package com.cit.pointage.ui.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cit.pointage.R;
import com.cit.pointage.model.response.HoraireResponse;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

/**
 * HoraireAdapter — RecyclerView Adapter pour la liste des horaires.
 * Chaque item affiche : catégorie, heure limite, jours, statut.
 * Le bouton "Désactiver" déclenche le callback OnDesactiverListener.
 */
public class HoraireAdapter extends
        RecyclerView.Adapter<HoraireAdapter.HoraireViewHolder> {

    /** Callback appelé quand l'Admin clique sur "Désactiver" */
    public interface OnDesactiverListener {
        void onDesactiver(HoraireResponse horaire);
    }

    private List<HoraireResponse> horaires = new ArrayList<>();
    private final OnDesactiverListener listener;

    public HoraireAdapter(OnDesactiverListener listener) {
        this.listener = listener;
    }

    /** Met à jour la liste et rafraîchit le RecyclerView */
    public void setHoraires(List<HoraireResponse> horaires) {
        this.horaires = horaires;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HoraireViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_horaire, parent, false);
        return new HoraireViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull HoraireViewHolder holder, int position) {
        holder.bind(horaires.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return horaires.size();
    }

    // ════════ ViewHolder ════════

    static class HoraireViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvCategorie;
        private final TextView tvHeureLimite;
        private final TextView tvJours;
        private final TextView tvStatut;
        private final MaterialButton btnDesactiver;

        HoraireViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategorie   = itemView.findViewById(R.id.tvCategorie);
            tvHeureLimite = itemView.findViewById(R.id.tvHeureLimite);
            tvJours       = itemView.findViewById(R.id.tvJours);
            tvStatut      = itemView.findViewById(R.id.tvStatut);
            btnDesactiver = itemView.findViewById(R.id.btnDesactiver);
        }

        void bind(HoraireResponse horaire,
                  OnDesactiverListener listener) {

            tvCategorie.setText(horaire.getCategorieLibelle());
            tvHeureLimite.setText(horaire.getHeureLimiteAffichage());
            tvJours.setText(horaire.getJoursAffichage());

            // Statut actif / inactif
            if (horaire.isActif()) {
                tvStatut.setText("Actif");
                tvStatut.setVisibility(View.VISIBLE);
                btnDesactiver.setEnabled(true);
            } else {
                tvStatut.setText("Inactif");
                btnDesactiver.setEnabled(false);
            }

            // Bouton désactiver
            btnDesactiver.setOnClickListener(v -> listener.onDesactiver(horaire));
        }
    }
}
