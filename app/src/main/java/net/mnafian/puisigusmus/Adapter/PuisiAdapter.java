package net.mnafian.puisigusmus.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.mnafian.puisigusmus.DialogPuisi;
import net.mnafian.puisigusmus.ItemClass.PuisiItem;
import net.mnafian.puisigusmus.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mnafian on 8/28/15.
 */
public class PuisiAdapter extends RecyclerView.Adapter<PuisiAdapter.PuisiHolder> {

    private List<PuisiItem> puisiList;
    private Context mContext;

    public PuisiAdapter(Context mContext, List<PuisiItem> puisiList) {
        this.puisiList = puisiList;
        this.mContext = mContext;
    }

    @Override
    public PuisiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.puisi_card_item, parent, false);
        PuisiHolder viewHolder = new PuisiHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PuisiHolder holder, int position) {
        final PuisiItem puisiItem = puisiList.get(position);
        holder.viewTittle.setText(puisiItem.getmJudulPuisi());
        holder.viewPengarang.setText(puisiItem.getmPengarang());

        Glide.with(mContext)
                .load(puisiItem.getmLinkImage())
                .into(holder.imageItem);

        holder.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogPuisi dialogPuisi = new DialogPuisi(mContext, puisiItem.getmPuisi(), puisiItem.getLinkPuisiDownload(), puisiItem.getmPengarang(), puisiItem.getmJudulPuisi(), puisiItem.getmLinkImage());
                dialogPuisi.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return puisiList.size();
    }

    public class PuisiHolder extends RecyclerView.ViewHolder {
        public TextView viewTittle;
        public TextView viewPengarang;

        public CircleImageView imageItem;
        public LinearLayout playButton;

        public PuisiHolder(View itemView) {
            super(itemView);
            viewTittle = (TextView) itemView.findViewById(R.id.puisi_judul);
            viewPengarang = (TextView) itemView.findViewById(R.id.puisi_pengarang);
            imageItem = (CircleImageView) itemView.findViewById(R.id.puisi_image);
            playButton = (LinearLayout) itemView.findViewById(R.id.play_puisi);
        }
    }
}
