package cn.ccrise.genki.mvp.home;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import cn.ccrise.baseframe.base.BaseActivity;
import cn.ccrise.baseframe.utils.ImageConstant;
import cn.ccrise.baseframe.utils.MLog;
import cn.ccrise.baseframe.utils.ToastUtil;
import cn.ccrise.genki.R;
import cn.ccrise.genki.util.ImageUtil;

public class MainActivity extends BaseActivity {
    @BindView(R.id.iv_debug)
    ImageView ivDebug;
    @BindView(R.id.text_tiew)
    TextView tv;
    @BindView(R.id.edit_text)
    EditText et;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        if("EditText".equals(name)){
            MLog.d("AppCompatViewInflater", "createEditText");
            TextInputEditText view = new TextInputEditText(context, attrs);
            TextInputLayout layout = new TextInputLayout(context, attrs);
            layout.setId(View.NO_ID);
            layout.addView(view);
            return layout;
        }
        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    protected void inject() {
        ivDebug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void init() {
        ImageUtil.loadRoundCorner(ivDebug, ImageConstant.IMAGE_HEAD);
        notHideKeyboardSet.add(ivDebug);
        toolbar.setOnMenuItemClickListener(item -> {
            ToastUtil.toast(item.getTitle());
            return true;
        });
        tv.setText(tv.getClass().getName());
        et.setText(et.getClass().getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_test, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
