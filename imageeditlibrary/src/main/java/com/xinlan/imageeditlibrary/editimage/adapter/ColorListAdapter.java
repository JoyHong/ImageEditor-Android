package com.xinlan.imageeditlibrary.editimage.adapter;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xinlan.imageeditlibrary.R;
import com.xinlan.imageeditlibrary.editimage.fragment.PaintFragment;
import com.xinlan.imageeditlibrary.editimage.model.PaintColorBean;

import java.util.ArrayList;


/**
 * 颜色列表Adapter
 *
 * @author panyi
 */
public class ColorListAdapter extends RecyclerView.Adapter<ViewHolder> {
    public static final int TYPE_COLOR = 1;
    public static final int TYPE_MORE = 2;

    public interface IColorListAction{
        void onColorSelected(final int position,final int color);
        void onMoreSelected(final int position);
    }

    private PaintFragment mContext;
    private int[] colorsData;
    private ArrayList<PaintColorBean> colorList;

    private IColorListAction mCallback;


    public ColorListAdapter(PaintFragment frg, int[] colors,IColorListAction action) {
        super();
        this.mContext = frg;
        this.colorsData = colors;
        this.mCallback = action;
    }

    public ColorListAdapter(PaintFragment frg, ArrayList<PaintColorBean> colorList, IColorListAction action) {
        super();
        this.mContext = frg;
        this.colorList = colorList;
        this.mCallback = action;
    }

    public class ColorViewHolder extends ViewHolder {
        View colorPanelView;
        public ColorViewHolder(View itemView) {
            super(itemView);
            this.colorPanelView = itemView.findViewById(R.id.color_panel_view);
        }
    }// end inner class

    public class MoreViewHolder extends ViewHolder {
        View moreBtn;
        public MoreViewHolder(View itemView) {
            super(itemView);
            this.moreBtn = itemView.findViewById(R.id.color_panel_more);
        }

    }//end inner class

    @Override
    public int getItemCount() {
        return colorList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return colorList.size() == position ? TYPE_MORE : TYPE_COLOR;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        ViewHolder viewHolder = null;
        if (viewType == TYPE_COLOR) {
            v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.image_editor_view_color_panel, parent,false);
            viewHolder = new ColorViewHolder(v);
        } else if (viewType == TYPE_MORE) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.image_editor_view_color_more_panel,parent,false);
            viewHolder = new MoreViewHolder(v);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if(type == TYPE_COLOR){
            onBindColorViewHolder((ColorViewHolder)holder,position);
        }else if(type == TYPE_MORE){
            onBindColorMoreViewHolder((MoreViewHolder)holder,position);
        }
    }

    private int perSelectedPosition = -1;
    private void onBindColorViewHolder(final ColorViewHolder holder,final int position){
//        holder.colorPanelView.setBackgroundColor(colorsData[position]);
        holder.colorPanelView.setBackgroundColor(colorList.get(position).color);
        if (colorList.get(position).selected){
            holder.colorPanelView.setScaleX(1.2f);
            holder.colorPanelView.setScaleY(1.2f);
        }else {
            holder.colorPanelView.setScaleX(1.0f);
            holder.colorPanelView.setScaleY(1.0f);
        }

        holder.colorPanelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCallback!=null){
                    mCallback.onColorSelected(position,colorList.get(position).color);
                    colorList.get(position).selected = true;
                    if (perSelectedPosition != -1){
                        colorList.get(perSelectedPosition).selected = false;
                    }
                    perSelectedPosition = position;
                    notifyDataSetChanged();
                }
            }
        });


    }

    private void onBindColorMoreViewHolder(final MoreViewHolder holder,final int position){
        holder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCallback!=null){
                    mCallback.onMoreSelected(position);
                }
            }
        });
    }

}// end class
