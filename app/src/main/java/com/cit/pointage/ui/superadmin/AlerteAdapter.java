package com.cit.pointage.ui.superadmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cit.pointage.R;
import com.cit.pointage.model.response.AlerteResponse;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class AlerteAdapter extends RecyclerView
        .Adapter<AlerteAdapter.ViewHolder> {

    private List<AlerteResponse> alertes =
            new ArrayList<>();
    private OnAlerteClickListener listener;

    public interface OnAlerteClickListener {
        void onMarquerLue(AlerteResponse alerte);
    }

    public AlerteAdapter(OnAlerteClickListener listener) {
        this.listener = listener;
    }

    public void setAlertes(List<AlerteResponse> liste) {
        this.alertes = liste;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                        parent.getContext())
                .inflate(R.layout.item_alerte,
                        parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder, int position) {
        holder.bind(alertes.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return alertes.size();
    }

    static class ViewHolder extends
            RecyclerView.ViewHolder {

        View dotNonLue;
        TextView tvTypePerte;
        TextView tvStatut;
        TextView tvUtilisateur;
        TextView tvDate;
        MaterialButton btnMarquerLue;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            dotNonLue = itemView.findViewById(
                    R.id.dotNonLue);
            tvTypePerte = itemView.findViewById(
                    R.id.tvTypePerte);
            tvStatut = itemView.findViewById(
                    R.id.tvStatut);
            tvUtilisateur = itemView.findViewById(
                    R.id.tvUtilisateur);
            tvDate = itemView.findViewById(
                    R.id.tvDate);
            btnMarquerLue = itemView.findViewById(
                    R.id.btnMarquerLue);
        }

        void bind(AlerteResponse alerte,
                  OnAlerteClickListener listener) {

            // Type de perte
            String type = "BADGE".equals(
                    alerte.getTypePerte())
                    ? "Perte de badge"
                    : "Perte de titre de permission";
            tvTypePerte.setText(type);

            // Utilisateur
            tvUtilisateur.setText(
                    alerte.getNomCompletUtilisateur());

            // Date
            String date = alerte.getEnvoyeeLe() != null
                    ? alerte.getEnvoyeeLe()
                    .substring(0, 16)
                    .replace("T", " à ")
                    : "";
            tvDate.setText(date);

            // Statut lu/non lu
            if (alerte.isLue()) {
                dotNonLue.setVisibility(View.INVISIBLE);
                tvStatut.setText("Lue");
                tvStatut.setTextColor(
                        itemView.getContext()
                                .getColor(
                                        R.color.ink_4));
                tvStatut.setBackgroundResource(
                        R.drawable.bg_statut_ok);
                btnMarquerLue.setVisibility(View.GONE);
            } else {
                dotNonLue.setVisibility(View.VISIBLE);
                tvStatut.setText("Non lue");
                tvStatut.setTextColor(
                        itemView.getContext()
                                .getColor(
                                        R.color.status_warn_fg));
                tvStatut.setBackgroundResource(
                        R.drawable.bg_statut_warn);
                btnMarquerLue.setVisibility(
                        View.VISIBLE);

                btnMarquerLue.setOnClickListener(
                        v -> listener.onMarquerLue(
                                alerte));
            }
        }
    }
}