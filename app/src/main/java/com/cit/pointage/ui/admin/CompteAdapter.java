package com.cit.pointage.ui.admin;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cit.pointage.R;
import com.cit.pointage.model.response.CompteResponse;

import java.util.ArrayList;
import java.util.List;

public class CompteAdapter extends RecyclerView.Adapter<CompteAdapter.ViewHolder> {

    public interface OnStatutChangeListener {
        void onStatutChange(CompteResponse compte, boolean activer);
    }

    private List<CompteResponse> comptes = new ArrayList<>();
    private final OnStatutChangeListener listener;

    public CompteAdapter(OnStatutChangeListener listener) {
        this.listener = listener;
    }

    public void setComptes(List<CompteResponse> comptes) {
        this.comptes = comptes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_compte, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CompteResponse compte = comptes.get(position);

        holder.tvNomComplet.setText(compte.getNomComplet());
        holder.tvLogin.setText("@" + compte.getLogin());
        holder.tvRole.setText(compte.getRole());

        // Icône et couleur selon le rôle
        boolean isAdmin = "ADMIN".equals(compte.getRole());
        holder.tvRoleIcon.setText(isAdmin ? "A" : "C");
        holder.tvRoleIcon.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(
                        isAdmin ? Color.parseColor("#1565C0") : Color.parseColor("#2E7D32")
                )
        );

        // Statut du switch
        boolean isActif = "ACTIF".equals(compte.getStatut());
        holder.switchStatut.setOnCheckedChangeListener(null); // éviter le listener lors du rebind
        holder.switchStatut.setChecked(isActif);

        holder.switchStatut.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (listener != null) {
                listener.onStatutChange(compte, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return comptes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoleIcon, tvNomComplet, tvLogin, tvRole;
        Switch switchStatut;

        ViewHolder(View view) {
            super(view);
            tvRoleIcon = view.findViewById(R.id.tvRoleIcon);
            tvNomComplet = view.findViewById(R.id.tvNomComplet);
            tvLogin = view.findViewById(R.id.tvLogin);
            tvRole = view.findViewById(R.id.tvRole);
            switchStatut = view.findViewById(R.id.switchStatut);
        }
    }
}
