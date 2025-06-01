package com.isaac.ggmanager.ui.home.team.member;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.isaac.ggmanager.databinding.ItemMemberBinding;
import com.isaac.ggmanager.domain.model.UserModel;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {
    @NonNull
    @Override
    public MemberAdapter.MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemMemberBinding binding = ItemMemberBinding.inflate(inflater, parent, false);
        return new MemberViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberAdapter.MemberViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MemberViewHolder extends RecyclerView.ViewHolder {
        private final ItemMemberBinding binding;

        public MemberViewHolder(ItemMemberBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(UserModel user) {
            //binding.ivProfileAvatar.setImageResource(?);
            binding.tvMemberName.setText(user.getName());
            binding.tvUserRole.setText("Member");
        }
    }
}
