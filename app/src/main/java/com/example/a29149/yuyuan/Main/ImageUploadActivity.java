package com.example.a29149.yuyuan.Main;

/**
 * Created by MaLei on 2017/5/22.
 * Email:ml1995@mail.ustc.edu.cn
 * 头像上传
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.UploadFile;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.controller.userInfo.GetPictureImageController;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUploadActivity extends Activity {
    private ImageView mImage;
    private Button mUpdateImage;
    private Button mAddImage;
    private Bitmap mBitmap;
    private String srcPath;
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    protected static Uri tempUri;
    private static final int CROP_SMALL_PICTURE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageupload);
        initUI();
        initListeners();
    }

    private void initUI() {
        mImage = (ImageView) findViewById(R.id.iv_image);
        mAddImage = (Button) findViewById(R.id.btn_add_image);
        mUpdateImage = (Button) findViewById(R.id.btn_update_image);
    }

    private void initListeners() {
        mAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChoosePicDialog();
            }
        });
        mUpdateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetSignAction().execute();
            }
        });
    }

    /**
     * 显示修改图片的对话框
     */
    protected void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ImageUploadActivity.this);
        builder.setTitle("添加图片");
        String[] items = {"选择本地照片", "拍照"};
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        Intent openAlbumIntent = new Intent(
                                Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        //用startActivityForResult方法，待会儿重写onActivityResult()方法，拿到图片做裁剪操作
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE: // 拍照
                        Intent openCameraIntent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/yuyuan/picture/";
                        tempUri = Uri.fromFile(new File(dir, "temp_image.jpg"));
                        // 将拍照所得的相片保存到SD卡根目录
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                        startActivityForResult(openCameraIntent, TAKE_PICTURE);
                        break;
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == MainStudentActivity.RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:
                    cutImage(tempUri); // 对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    cutImage(data.getData()); // 对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }

    /**
     * 裁剪图片方法实现
     */
    protected void cutImage(Uri uri) {
        if (uri == null) {
            Log.i("alanjet", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        //com.android.camera.action.CROP这个action是用来裁剪图片用的
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    /**
     * 保存裁剪之后的图片数据
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            mBitmap = extras.getParcelable("data");
            //这里图片是方形的，可以用一个工具类处理成圆形（很多头像都是圆形，这种工具类网上很多不再详述）
            mImage.setImageBitmap(mBitmap);//显示图片
            //保存图片至sd卡
            saveBitmap();

        }
    }

    public void saveBitmap() {
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/picture/";
        File f = new File(dir  + "temp_image.jpg" );
        srcPath = f.getPath();
        Log.i("malei","srcPath="+srcPath);
        if (!f.exists()) {
            boolean flag = f.getParentFile().mkdirs();
            System.out.println(this.getClass() + "  结果是  " + flag);

        }
        try {
            f.createNewFile();
            FileOutputStream out = new FileOutputStream(f);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * 获取腾讯云COS签名
     */
    private class GetSignAction extends AsyncTask<String, Integer, String> {

        GetSignAction() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {

            String result = GetPictureImageController.execute();
            return result;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            log.d(this, result);
            if (result != null) {
                try {

                    JSONObject jsonObject = new JSONObject(result);

                    String resultFlag = jsonObject.getString("result");
                    String sign = jsonObject.getString("sign");
//                    sign = URLDecoder.decode(sign, "utf-8");
//                    sign = new String( ConverterByteBase64.base642Byte(sign), "utf-8" );
                    Log.i("malei","sign="+sign);

                    if (resultFlag.equals("success")) {
                        GlobalUtil.getInstance().setSign(sign);
                        UploadFile uploadFile = new UploadFile(ImageUploadActivity.this,GlobalUtil.getInstance().getSign());
                        Log.i("malei", srcPath);
                        uploadFile.updata(srcPath);
                    }
                } catch (Exception e) {
                    Toast.makeText(ImageUploadActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
                }

            }

        }
    }
}



