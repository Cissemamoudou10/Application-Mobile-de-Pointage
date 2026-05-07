package com.cit.pointage.ui.superadmin;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cit.pointage.R;
import com.cit.pointage.databinding.ItemDetailPresenceBinding;
import com.cit.pointage.model.response.DetailPresenceResponse;

import java.util.ArrayList;
import java.util.List;

public class DetailPresenceAdapter extends RecyclerView.Adapter<DetailPresenceAdapter.ViewHolder> {

    private List<DetailPresenceResponse> details = new ArrayList<>();

    public void setDetails(List<DetailPresenceResponse> details) {
        this.details = details;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDetailPresenceBinding binding = ItemDetailPresenceBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(details.get(position));
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemDetailPresenceBinding binding;

        ViewHolder(ItemDetailPresenceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(DetailPresenceResponse detail) {
            binding.tvNomComplet.setText(detail.getNomComplet());
            binding.tvMatricule.setText(detail.getMatricule());

            if (detail.getNomComplet() != null && !detail.getNomComplet().isEmpty()) {
                binding.tvInitiale.setText(String.valueOf(detail.getNomComplet().charAt(0)).toUpperCase());
            }

            if (detail.getHeurePointage() != null && !detail.getHeurePointage().isEmpty()) {
                binding.tvHeure.setText(detail.getHeurePointage());
                binding.tvHeure.setVisibility(android.view.View.VISIBLE);
            } else {
                binding.tvHeure.setVisibility(android.view.View.GONE);
            }

            // Statut
            String statut = detail.getStatutDuJour();
            binding.tvStatut.setText(statut);

            int bgRes = R.drawable.bg_statut_ok;
            int colorRes = R.color.ink_1;

            if ("PRESENT".equals(statut)) {
                bgRes = R.drawable.bg_statut_ok;
                colorRes = R.color.status_ok_fg;
            } else if ("RETARD".equals(statut)) {
                bgRes = R.drawable.bg_statut_warn;
                colorRes = R.color.status_warn_fg;
            } else if ("ABSENT".equals(statut)) {
                bgRes = R.drawable.bg_statut_err;
                colorRes = R.color.status_err_fg;
            } else if ("PERMISSION".equals(statut)) {
                // Violet
                bgRes = R.drawable.bg_statut_connexion; // ou autre
                colorRes = R.color.status_info_fg;
                binding.tvStatut.setTextColor(Color.parseColor("#7E22CE"));
                binding.tvStatut.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F3E8FF")));
            }

            if (!"PERMISSION".equals(statut)) {
                binding.tvStatut.setBackgroundResource(bgRes);
                binding.tvStatut.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), colorRes));
                binding.tvStatut.setBackgroundTintList(null); // Reset
            }
        }
    }
}
