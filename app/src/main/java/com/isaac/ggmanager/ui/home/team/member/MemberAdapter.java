package com.isaac.ggmanager.ui.home.team.member;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.isaac.ggmanager.databinding.ItemMemberBinding;
import com.isaac.ggmanager.domain.model.Avatar;
import com.isaac.ggmanager.domain.model.UserModel;

import java.util.List;

/**
 * Adaptador para RecyclerView que muestra una lista de miembros de un equipo.
 * Cada elemento muestra el avatar, nombre y rol dentro del equipo del usuario.
 */
public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {

    private List<UserModel> members;

    /**
     * Constructor del adaptador.
     *
     * @param members Lista inicial de miembros a mostrar.
     */
    public MemberAdapter(List<UserModel> members){
        this.members = members;
    }

    @NonNull
    @Override
    public MemberAdapter.MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemMemberBinding binding = ItemMemberBinding.inflate(inflater, parent, false);
        return new MemberViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberAdapter.MemberViewHolder holder, int position) {
        holder.bind(members.get(position));
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    /**
     * Actualiza la lista de miembros y notifica al RecyclerView que los datos han cambiado.
     *
     * @param newMembers Nueva lista de miembros.
     */
    public void updateData(List<UserModel> newMembers){
        this.members = newMembers;
        notifyDataSetChanged();
    }

    /**
     * ViewHolder que representa cada ítem de miembro en el RecyclerView.
     */
    public static class MemberViewHolder extends RecyclerView.ViewHolder {
        private final ItemMemberBinding binding;

        public MemberViewHolder(ItemMemberBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * Enlaza los datos de un usuario con los elementos visuales del ítem.
         *
         * @param user Usuario a mostrar.
         */
        public void bind(UserModel user) {
            Avatar avatar = Avatar.fromKey(user.getAvatar());
            binding.ivProfileAvatar.setImageResource(avatar.getDrawableResId());
            binding.tvMemberName.setText(user.getName());
            binding.tvUserRole.setText(user.getTeamRole());
        }
    }
}
