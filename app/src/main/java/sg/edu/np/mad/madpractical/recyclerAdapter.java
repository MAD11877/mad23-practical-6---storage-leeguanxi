package sg.edu.np.mad.madpractical;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<User> userList;
    private Context context;
    private final int VIEW_TYPE_NORMAL = 0;
    private final int VIEW_TYPE_SPECIAL = 1;

    public recyclerAdapter(ArrayList<User> userList, Context context){

        this.userList = userList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position){
        if (userList.get(position).getName().endsWith("7")){
            return VIEW_TYPE_SPECIAL;
        } else return VIEW_TYPE_NORMAL;
    }

    //@NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch(viewType) {
            case VIEW_TYPE_NORMAL: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list, parent, false);
                return new NormalViewHolder(view);
            }


            case VIEW_TYPE_SPECIAL: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_7, parent, false);
                return new SpecialViewHolder(view);
            }
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch(holder.getItemViewType()){
            case VIEW_TYPE_NORMAL: {
                NormalViewHolder normalViewHolder = (NormalViewHolder) holder;
                String name = userList.get(position).getName();
                String description = userList.get(position).getDescription();
                normalViewHolder.name.setText(name);
                normalViewHolder.description.setText(description);
                break;
            }

            case VIEW_TYPE_SPECIAL:{
                SpecialViewHolder specialViewHolder = (SpecialViewHolder) holder;
                String name = userList.get(position).getName();
                String description = userList.get(position).getDescription();
                specialViewHolder.name.setText(name);
                specialViewHolder.description.setText(description);
                break;
            }

        }
        //String name = userList.get(position).getName();
        //String description = userList.get(position).getDescription();

        //holder.name.setText(name);
        //holder.description.setText(description);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    // end with 7 layout (user_list_7.xml)
    class SpecialViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView description;
        public SpecialViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.nameTV);
            description= itemView.findViewById(R.id.descriptionTV);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                User user = userList.get(position);
                showAlertDialog(user);
            }
        }
    }

    // normal layout (user_list.xml)
    class NormalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView description;
        public NormalViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.nameTV);
            description= itemView.findViewById(R.id.descriptionTV);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                User user = userList.get(position);
                showAlertDialog(user);
            }
        }
    }
    /*
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameTV);
            description= itemView.findViewById(R.id.descriptionTV);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                User user = userList.get(position);
                showAlertDialog(user);
            }
        }
    } */

    private void showAlertDialog(User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Profile")
                .setMessage(user.name)
                .setPositiveButton("View", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("ID", user.id);
                        intent.putExtra("USERNAME", user.name);
                        intent.putExtra("DESCRIPTION", user.description);
                        intent.putExtra("FOLLOWED", user.followed);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("Close", null)
                .show();
    }



}
