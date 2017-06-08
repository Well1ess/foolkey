package com.example.a29149.yuyuan.Main;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.resource.R;
import com.example.resource.util.image.ImageService;

import java.io.ByteArrayOutputStream;

/***
 * 只做了调用摄像头拍照，调用相册复用即可，我留好接口
 * 接口所在之地为TODO标注（TODO标签表示将要做还未做，或者做的不好的）
 * 头像上传by张丽华
 */
public class UploadImageActivity extends AppCompatActivity implements View.OnClickListener {

    private final static int SELECT_CAMERA = 0;//调用相机
    private final static int SELECT_PIC = 1;//调用相册


    private LinearLayout mllyt_photoPanel;//头像布局
    private ImageView miv_photoEdit;//头像

    private CharSequence[] its = {"拍照", "从相册选择"};//弹出框
    private Bitmap photo;//暂存拍照传过来的图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        initViews();
        initData();
    }

    /**
     * 初始化组件
     */
    private void initViews() {
        mllyt_photoPanel = (LinearLayout) findViewById(R.id.user_top);
        miv_photoEdit = (ImageView) findViewById(R.id.user_top_edit);
    }

    /**
     * 数据初始化
     */
    private void initData() {
        mllyt_photoPanel.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;
        switch (requestCode) {
            case UploadImageActivity.SELECT_PIC://相册
                //TODO：把愚猿旧的集成过来即可
                break;
            case UploadImageActivity.SELECT_CAMERA://相机

                Bundle bundle = data.getExtras();
                photo = (Bitmap) bundle.get("data");
                miv_photoEdit.setImageBitmap(photo);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                //一般Png都比Jpeg大
                photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                Log.d("Tag", baos.toByteArray().length / 1024 + "");

                baos.reset();
                photo = ImageService.getCompress(photo, ImageService.ImageKind.ICON);
                miv_photoEdit.setImageBitmap(photo);

                photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                Log.d("Tag", baos.toByteArray().length / 1024 + "");
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.user_top)
            new AlertDialog.Builder(UploadImageActivity.this)
                    .setTitle("更换头像")
                    .setItems(its, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            switch (which) {
                                case 0://拍照//图片名称 时间命名
                                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(intent, UploadImageActivity.SELECT_CAMERA);
                                    break;
                                case 1://从相册选择
                                    //TODO：把愚猿旧的集成过来即可
                                    intent.setAction(Intent.ACTION_OPEN_DOCUMENT);//ACTION_OPEN_DOCUMENT
                                    intent.setType("image/*");
                                    startActivityForResult(intent, UploadImageActivity.SELECT_PIC);
                                    break;
                            }
                        }
                    }).create().show();


    }
}
