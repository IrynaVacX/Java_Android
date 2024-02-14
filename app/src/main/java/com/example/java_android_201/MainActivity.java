package com.example.java_android_201;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

class WishlistItem
{
    private boolean isChecked;
    private int imageId;
    private String title;
    private double price;

    public WishlistItem(String title, double price, int imageId) {
        this.isChecked = false;
        this.imageId = imageId;
        this.title = title;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }
    public double getPrice() {
        return price;
    }
    public int getImageId() {
        return imageId;
    }
    public boolean isChecked() {
        return isChecked;
    }
}

class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.WishlistViewHolder>
{
    private List<WishlistItem> wishList;
    WishListAdapter(List<WishlistItem> wishList)
    {
        this.wishList = wishList;
    }

    @Override
    public WishlistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_item, parent, false);
        return new WishlistViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(WishlistViewHolder holder, int position) {
        WishlistItem wishlistItem = wishList.get(position);
        holder._checkBox.setChecked(wishlistItem.isChecked());
        holder._image.setImageResource(wishlistItem.getImageId());
        holder._title.setText(wishlistItem.getTitle());
        holder._price.setText(Double.toString(wishlistItem.getPrice()));
    }
    @Override
    public int getItemCount() {
        return wishList.size();
    }

    class WishlistViewHolder extends RecyclerView.ViewHolder
    {
        GridLayout _gridlayout;
        CheckBox _checkBox;
        ImageView _image;
        TextView _title;
        TextView _price;

        WishlistViewHolder(View view)
        {
            super(view);
            _gridlayout = view.findViewById(R.id.griditem);
            _checkBox = view.findViewById(R.id.checkbox);
            _image = view.findViewById(R.id.image);
            _title = view.findViewById(R.id.tv_title);
            _price = view.findViewById(R.id.tv_price);

            _checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    if (isChecked)
                    {
                        _gridlayout.setBackgroundResource(R.drawable.rounded_border_2);
                    }
                    else
                    {
                        _gridlayout.setBackgroundResource(R.drawable.rounded_border);
                    }
                    TextView totalSumTextView = view.getRootView().findViewById(R.id.tv_totalsum);
                    double totalSum = ChangeTotalSum(String.valueOf(totalSumTextView.getText()), isChecked);
                    totalSumTextView.setText("Total : ₴" + String.format("%.2f", totalSum));
                }
            });

        }
        public double ChangeTotalSum(String text, boolean isMinus)
        {
            String[] parts = text.split("₴");
            double res = Double.parseDouble(parts[1].replace(",", "."));
            if (isMinus)
            {
                res -= Double.parseDouble((String) _price.getText());
            }
            else
            {
                res += Double.parseDouble((String) _price.getText());
            }
            return res;
        }
    }
}

public class MainActivity extends AppCompatActivity {
    private List<WishlistItem> list = new ArrayList<>();
    private WishListAdapter a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rv = findViewById(R.id.recyclerView);

        a = new WishListAdapter(list);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(lm);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(a);

        prepareWishlistItemData();

        double totalSum = calculateTotalSum();
        TextView totalSumTextView = findViewById(R.id.tv_totalsum);
        totalSumTextView.setText("Total : ₴" + String.format("%.2f", totalSum));
    }

    private void prepareWishlistItemData()
    {
        list.add(new WishlistItem("Автомобильная утка у шоломі Шалена качка", 199, R.drawable.duck));
        list.add(new WishlistItem("Танцюючий Кактус повторюшка", 585, R.drawable.cactus));
        list.add(new WishlistItem("Воскреситель: Анатомія фантастичних істот - Е. Б. Гадспет", 480, R.drawable.book));
        list.add(new WishlistItem("Риба м'яка, лосось 20см КР65", 60, R.drawable.fish));
        list.add(new WishlistItem("Цукерки глазуровані Roshen Konafetto Bianco 1 кг", 247, R.drawable.candies));

        a.notifyDataSetChanged();
    }

    private double calculateTotalSum()
    {
        double totalSum = 0.0;

        for (WishlistItem item : list)
        {
            if(!item.isChecked())
            {
                totalSum += item.getPrice();
            }
        }

        return totalSum;
    }
}
