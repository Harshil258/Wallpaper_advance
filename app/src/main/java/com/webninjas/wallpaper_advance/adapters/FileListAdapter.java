package com.webninjas.wallpaper_advance.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.webninjas.wallpaper_advance.FileListClickInterface;
import com.webninjas.wallpaper_advance.R;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;


public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<File> fileArrayList;
    private LayoutInflater layoutInflater;
    private FileListClickInterface fileListClickInterface;

    public FileListAdapter(Context context, ArrayList<File> files, FileListClickInterface fileListClickInterface) {
        this.context = context;
        this.fileArrayList = files;
        this.fileListClickInterface = fileListClickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.items_file_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") int i) {
        File fileItem = fileArrayList.get(i);

        try {
            String extension = fileItem.getName().substring(fileItem.getName().lastIndexOf("."));
            if (extension.equals(".mp4")) {
                viewHolder.ivPlay.setVisibility(View.VISIBLE);
            } else {
                viewHolder.ivPlay.setVisibility(View.GONE);
            }
//            Picasso.get().load(fileItem.getPath()).into(viewHolder.pc);
            Glide.with(context)
                    .load(fileItem.getPath())
                    .into(viewHolder.pc);

        } catch (Exception ex) {
        }

        viewHolder.rl_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileListClickInterface.getPosition(i, fileItem);
            }
        });
    }


    @Override
    public int getItemCount() {
        return fileArrayList == null ? 0 : fileArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPlay, pc;
        RelativeLayout rl_main;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            ivPlay = itemView.findViewById(R.id.iv_play);
            pc = itemView.findViewById(R.id.pc);
            rl_main = itemView.findViewById(R.id.rl_main);

        }
    }
}