package com.cit.pointage.ui.superadmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cit.pointage.R;
import com.cit.pointage.model.response.PerteResponse;

import java.util.ArrayList;
import java.util.List;

public class PerteAdapter extends RecyclerView.Adapter<PerteAdapter.ViewHolder> {

    private List<PerteResponse> pertes = new ArrayList<>();

    public void setPertes(List<PerteResponse> liste) {
        this.pertes = liste;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_perte, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(pertes.get(position));
    }

    @Override
    public int getItemCount() {
        return pertes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNomUtilisateur;
        TextView tvMatricule;
        TextView tvStatut;
        TextView tvTypePerte;
        TextView tvDate;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomUtilisateur = itemView.findViewById(R.id.tvNomUtilisateur);
            tvMatricule = itemView.findViewById(R.id.tvMatricule);
            tvStatut = itemView.findViewById(R.id.tvStatut);
            tvTypePerte = itemView.findViewById(R.id.tvTypePerte);
            tvDate = itemView.findViewById(R.id.tvDate);
        }

        void bind(PerteResponse perte) {
            tvNomUtilisateur.setText(perte.getNomCompletUtilisateur());
            tvMatricule.setText(perte.getMatricule());

            // Type de perte
            String type = "BADGE".equals(perte.getTypePerte())
                    ? "Perte de badge" : "Perte de titre de permission";
            tvTypePerte.setText(type);

            // Statut
            if (perte.isTraitee()) {
                tvStatut.setText("Traitée");
                tvStatut.setTextColor(itemView.getContext().getColor(R.color.ink_4));
                tvStatut.setBackgroundResource(R.drawable.bg_statut_ok);
            } else {
                tvStatut.setText("Non traitée");
                tvStatut.setTextColor(itemView.getContext().getColor(R.color.status_warn_fg));
                tvStatut.setBackgroundResource(R.drawable.bg_statut_warn);
            }

            // Date
            String dateStr = perte.getDateSignalement() != null
                    ? perte.getDateSignalement().substring(0, 16).replace("T", " à ")
                    : "—";
            tvDate.setText(dateStr);
        }
    }
}
