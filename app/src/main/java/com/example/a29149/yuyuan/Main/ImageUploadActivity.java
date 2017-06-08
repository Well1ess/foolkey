package com.example.a29149.yuyuan.Main;

/**
 * Created by MaLei on 2017/5/22.
 * Email:ml1995@mail.ustc.edu.cn
 * 头像上传
 */

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.UploadFile;
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.a29149.yuyuan.controller.userInfo.GetPictureImageController;
import com.example.resource.component.baseObject.AbstractActivity;
import com.example.resource.component.baseObject.AbstractAppCompatActivity;
import com.example.resource.util.image.GlideCircleTransform;
import com.example.resource.util.image.ImageService;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageUploadActivity extends AbstractActivity {
    private ImageView mImage;//将要上传的头像
    private Button mUpdateImage;//上传按钮
    private Button mAddImage;//添加头像按钮
    private Bitmap mBitmap;
    private String srcPath;//上传头像的本地路径
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    protected static Uri tempUri;
    private static final int CROP_SMALL_PICTURE = 2;
    private Bitmap photo;//暂存拍照传过来的图片
    private String userName =
            GlobalUtil.getInstance().getStudentDTO().getUserName();
    private RequestManager glide;
    // 要申请的权限
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private AlertDialog dialog;


//    private boolean hasPhoto

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageupload);
        initUI();
        initListeners();
        System.out.println(this.getClass() + "60行看一下Global里的学生");
        System.out.println(GlobalUtil.getInstance().getStudentDTO());
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (true || Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(ImageUploadActivity.this, permissions[0]);
            Log.i("malei","动态申请权限"+i);
            Log.i("malei","PackageManager.PERMISSION_GRANTED="+PackageManager.PERMISSION_GRANTED);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                Log.i("malei","PackageManager.PERMISSION_GRANTED="+PackageManager.PERMISSION_GRANTED);
                showDialogTipUserRequestPermission();
            }
        }
    }

    private void initUI() {
        mImage = (ImageView) findViewById(R.id.iv_image);
        mAddImage = (Button) findViewById(R.id.btn_add_image);
        mUpdateImage = (Button) findViewById(R.id.btn_update_image);

        glide = Glide.with(this);
        if (GlobalUtil.getInstance().getStudentDTO() != null) {
            glide.load(PictureInfoBO.getOnlinePhoto(GlobalUtil.getInstance().getStudentDTO().getUserName()))
                    .error(R.drawable.photo_placeholder1)
                    .transform(new GlideCircleTransform(this))
                    .into(mImage);

        } else {
            mImage.setBackgroundColor(Color.parseColor("#FOFOFO"));
        }

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
        //String[] items = {"选择本地照片"};
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
                        File file = new File(dir);
                        if(!file.exists()){
                            file.mkdir();
                        }
                        file = new File(dir, checkUserName(new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())+ ".JPEG"));
                        tempUri = Uri.fromFile(file);

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
        if (requestCode == 123) {

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 检查该权限是否已经获取
                int i = ContextCompat.checkSelfPermission(this, permissions[0]);
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (i != PackageManager.PERMISSION_GRANTED) {
                    // 提示用户应该去应用设置界面手动开启权限
                    showDialogTipUserGoToAppSettting();
                } else {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
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

    /**
     * 保存并压缩
     */
    public void saveBitmap() {
        String path = checkUserName(userName);
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/picture/";
        File f = new File(dir + path);
        srcPath = f.getPath();
        Log.i("malei", "srcPath=" + srcPath);
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
            System.out.println(this.getClass() + "226行看一下Global");
            System.out.println(GlobalUtil.getInstance().getStudentDTO());
            String path = checkUserName(userName);
            super.onPostExecute(result);
//            log.d(this, result);
            if (result != null) {
                try {

                    JSONObject jsonObject = new JSONObject(result);

                    String resultFlag = jsonObject.getString("result");
                    String sign = jsonObject.getString("sign");
//                    sign = URLDecoder.decode(sign, "utf-8");
//                    sign = new String( ConverterByteBase64.base642Byte(sign), "utf-8" );
                    Log.i("malei", "sign=" + sign);

                    if (resultFlag.equals("success")) {
                        GlobalUtil.getInstance().setSign(sign);
                        UploadFile uploadFile = new UploadFile(ImageUploadActivity.this, GlobalUtil.getInstance().getSign());
                        Log.i("malei", this.getClass() + "240行 " + path);
                        uploadFile.updata(srcPath, path);
                        //上传完毕，退出本activity
                        finish();
                    }
                } catch (Exception e) {
                    Toast.makeText(ImageUploadActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(ImageUploadActivity.this, "无法连接到网络\n请检查网络设置", Toast.LENGTH_LONG).show();
            }

        }
    }

    /**
     * 判断用户名
     * 默认是获取全局的
     * 如果是注册时候，就得是用户输入的
     * 如果没有注册，全局也是空，则会叫default
     *
     * @param userName
     *
     * @return
     */
    private String checkUserName(String userName) {
      /*  if (getIntent().getStringExtra("userName") != null)
            userName = getIntent().getStringExtra("userName");
        return PictureInfoBO.getUrlForUpload(userName);*/
      return userName;
//        String phone;
//        if (userName == null){
//            userName = "default";
//        }
//        if (getIntent().getStringExtra("userName") == null){
//            phone = userName;
//        }else {
//            phone = getIntent().getStringExtra("userName");
//            phone = PictureInfoBO.getUrlForUpload(phone);
//        }
//        System.out.println(this.getClass() + "270" + phone);
//        return phone;
    }

        // 提示用户该请求权限的弹出框
        private void showDialogTipUserRequestPermission() {

            new AlertDialog.Builder(this)
                    .setTitle("存储权限不可用")
                    .setMessage("由于存储需要获取存储空间，为你存储个人信息；\n否则，您将无法正常使用")
                    .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startRequestPermission();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setCancelable(false).show();
        }

        // 开始提交请求权限
        private void startRequestPermission() {
            ActivityCompat.requestPermissions(this, permissions, 321);
        }

        // 用户权限 申请 的回调方法
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            if (requestCode == 321) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                        boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                        if (!b) {
                            // 用户还是想用我的 APP 的
                            // 提示用户去应用设置界面手动开启权限
                            showDialogTipUserGoToAppSettting();
                        } else
                            finish();
                    } else {
                        Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        // 提示用户去应用设置界面手动开启权限

        private void showDialogTipUserGoToAppSettting() {

            dialog = new AlertDialog.Builder(this)
                    .setTitle("存储权限不可用")
                    .setMessage("请在-应用设置-权限-中，允许愚猿使用存储权限来保存用户数据")
                    .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 跳转到应用设置界面
                            goToAppSetting();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setCancelable(false).show();
        }

        // 跳转到当前应用的设置界面
        private void goToAppSetting() {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivityForResult(intent, 123);
        }

        //



}



