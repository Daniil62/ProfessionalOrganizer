package ru.job4j.professional_organizer;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SpecialAdapter extends RecyclerView.Adapter<SpecialAdapter.SpecialHolder> {
    private final SpecialistsFragment.SpecSelect select;
    private static int id;
    public static int getId() {
        return id;
    }
    public static void setId(int value) {
        id = value;
    }
    SpecialAdapter(SpecialistsFragment.SpecSelect select) {
        this.select = select;
    }
    @NonNull
    @Override
    public SpecialHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SpecialHolder(LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.specialist_module, viewGroup, false));
    }
    @Override
    public void onBindViewHolder(@NonNull SpecialHolder holder, int i) {
        holder.bind(SpecialistsFragment.getList().get(i), i);
        holder.itemView.setId(i);
        holder.itemView.setOnClickListener(v -> {SpecialAdapter.setId(holder.itemView.getId());
            select.selected(SpecialAdapter.getId());});
    }
    @Override
    public int getItemCount() {
        return SpecialistsFragment.getList().size();
    }
    static class SpecialHolder extends RecyclerView.ViewHolder{
        private final TextView nameText;
        private final TextView surnameText;
        private final ImageView photo;
        SpecialHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.previewName);
            surnameText = itemView.findViewById(R.id.previewSurname);
            photo = itemView.findViewById(R.id.previewPhoto);
        }
        void bind(Specialist specialist, int i) {
            nameText.setText(specialist.getName());
            surnameText.setText(specialist.getSurname());
            photo.setImageResource(specialist.getPhotoId());
            nameText.setId(i);
            surnameText.setId(i);
            photo.setId(i);
        }
    }
}
