package com.isaac.ggmanager.ui.home.team.member;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.isaac.ggmanager.databinding.ItemMemberBinding;
import com.isaac.ggmanager.domain.model.Avatar;
import com.isaac.ggmanager.domain.model.UserModel;

import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {

    private List<UserModel> members;

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

    public void updateData(List<UserModel> newMembers){
        this.members = newMembers;
        notifyDataSetChanged();
    }

    public static class MemberViewHolder extends RecyclerView.ViewHolder {
        private final ItemMemberBinding binding;

        public MemberViewHolder(ItemMemberBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(UserModel user) {
            Avatar avatar = Avatar.fromKey(user.getAvatar());
            binding.ivProfileAvatar.setImageResource(avatar.getDrawableResId());
            binding.tvMemberName.setText(user.getName());
            binding.tvUserRole.setText(user.getName());
        }
    }
}
