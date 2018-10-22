package rendulic.igor.foodrecognizerexample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import rendulic.igor.DatabaseHelper.DataModel;

/**
 * Created by Wasim on 5/30/2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context context;
    private ArrayList<DataModel> dataModels;

    public MyAdapter(Context context, ArrayList<DataModel> dataModels) {
        this.context = context;
        this.dataModels = dataModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_layout, parent, false);
        return new ViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DataModel dataModel = dataModels.get(position);
        holder.txtItemName.setText(dataModel.getName());
        holder.txtCalories.setText("Calories : " + dataModel.getCalories());
        holder.txtFat.setText("Fat :                                " + dataModel.getFat());
        holder.txtCholesterol.setText("Cholesterol :                 " + dataModel.getCholestrol());
        holder.txtSodium.setText("Sodium :                        " + dataModel.getSodium());
        holder.txtPotassium.setText("Potassium :                  " + dataModel.getPotassium());
        holder.txtCarbohydrates.setText("Carbohydrates :           " + dataModel.getCarbohydrates());
        holder.txtProteins.setText("Proteins :                      " + dataModel.getProteins());
    }

    @Override
    public int getItemCount() {
        return dataModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtItemName, txtCalories, txtFat, txtCholesterol, txtSodium, txtPotassium, txtCarbohydrates, txtProteins;

        public ViewHolder(View itemView) {
            super(itemView);
            txtItemName = itemView.findViewById(R.id.txtItemName);
            txtCalories = itemView.findViewById(R.id.txtCalories);
            txtFat = itemView.findViewById(R.id.txtFat);
            txtCholesterol = itemView.findViewById(R.id.txtCholesterol);
            txtSodium = itemView.findViewById(R.id.txtSodium);
            txtPotassium = itemView.findViewById(R.id.txtPotassium);
            txtCarbohydrates = itemView.findViewById(R.id.txtCarbohydrates);
            txtProteins = itemView.findViewById(R.id.txtProteins);
        }
    }
}
