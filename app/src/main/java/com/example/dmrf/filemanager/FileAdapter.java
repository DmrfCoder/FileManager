package com.example.dmrf.filemanager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

/**
 * Created by DMRF on 2017/7/11.
 */

class FileAdapter extends BaseAdapter {
    //定义返回键、各种格式的文件的图标
    private Bitmap mBackRoot;
    private Bitmap mBackUp;
    private Bitmap mImage;
    private Bitmap mAudio;
    private Bitmap mRar;
    private Bitmap mVideo;
    private Bitmap mFolder;
    private Bitmap mApk;
    private Bitmap mOthers;
    private Bitmap mTxt;
    private Bitmap mWeb;

    //文件名列表
    private List<String> mFileNameList;
    //文件路径列表
    private List<String> mFilePathList;

    private Context mContext;

    public FileAdapter(Context context, List<String> fileName, List<String> filePath) {
        mContext = context;
        mFileNameList = fileName;
        mFilePathList = filePath;

        //初始化图片资源:

        // 返回到根目录
        mBackRoot = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.back_to_up);

        //返回到上一级目录
        mBackUp = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.back_to_up);

        //图片文件对应的icon
        mImage = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.image);

        //音频文件对应的图片
        mAudio = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.audio);

        //视频文件对应的icon
        mVideo = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.video);

        //可执行文件对应的icon
        mApk = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.apk);

        //文本文件对应的icon
        mTxt = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.txt);

        //其他类型文件对应的icon
        mOthers = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.others);

        //文件夹对应的icon
        mFolder = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.folder);

        //zip文件对应的icon
        mRar = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.zip_icon);

        //网页文件对应的icon
        mWeb = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.web_browser);
    }

    //获得文件的总数
    @Override
    public int getCount() {
        return mFileNameList.size();
    }

    //获得当前位置对应的文件名
    @Override
    public Object getItem(int i) {
        return mFileNameList.get(i);
    }

    //获得当前位置
    @Override
    public long getItemId(int i) {
        return i;
    }

    //获得视图
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder mViewHolder = null;
        if (view == null) {
            mViewHolder = new ViewHolder();
            LayoutInflater mLI = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //初始化列表元素界面
            view = mLI.inflate(R.layout.list_child, null);
            //初始化列表布局界面元素
            mViewHolder.mIV = view.findViewById(R.id.image_list_childs);
            mViewHolder.mTV = view.findViewById(R.id.text_list_childs);
            //将每一行的元素设置成标签
            view.setTag(mViewHolder);
        } else {
            //获取视图标签
            mViewHolder = (ViewHolder) view.getTag();
        }

        File mFile = new File(mFilePathList.get(i).toString());
        //如果当前单击的时返回根目录
        if (mFileNameList.get(i).toString().equals("BackToRoot")) {
            //添加返回根目录的按钮
            mViewHolder.mIV.setImageBitmap(mBackRoot);
            mViewHolder.mTV.setText("返回根目录");
        } else if (mFileNameList.get(i).toString().equals("BackToUp")) {
            //添加返回上一层按钮
            mViewHolder.mIV.setImageBitmap(mBackUp);
            mViewHolder.mTV.setText("返回上一级");
        } else if (mFileNameList.get(i).toString().equals("BacktoSearchBefore")) {
            //添加返回搜索之前目录的按钮
            mViewHolder.mIV.setImageBitmap(mBackRoot);
            mViewHolder.mTV.setText("返回搜索之前的目录");
        } else {
            String fileName = mFile.getName();
            mViewHolder.mTV.setText(fileName);
            if (mFile.isDirectory()) {
                mViewHolder.mIV.setImageBitmap(mFolder);
            } else {
                //取出文件后缀并转化为小写
                String FileEnds = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
                if (FileEnds.equals("m4a") || FileEnds.equals("mp3") || FileEnds.equals("mid") || FileEnds.equals("xmf") || FileEnds.equals("ogg") || FileEnds.equals("wav")) {
                    mViewHolder.mIV.setImageBitmap(mVideo);
                } else if (FileEnds.equals("3gp") || FileEnds.equals("mp4")) {
                    mViewHolder.mIV.setImageBitmap(mAudio);
                } else if (FileEnds.equals("jpg") || FileEnds.equals("gif") || FileEnds.equals("png") || FileEnds.equals("jpeg") || FileEnds.equals("bmp")) {
                    mViewHolder.mIV.setImageBitmap(mImage);
                } else if (FileEnds.equals("apk")) {
                    mViewHolder.mIV.setImageBitmap(mApk);
                } else if (FileEnds.equals("zip") || FileEnds.equals("rar")) {
                    mViewHolder.mIV.setImageBitmap(mRar);
                } else if (FileEnds.equals("html") || FileEnds.equals("htm") || FileEnds.equals("mht")) {
                    mViewHolder.mIV.setImageBitmap(mWeb);
                } else {
                    mViewHolder.mIV.setImageBitmap(mOthers);
                }
            }
        }

        return view;
    }

    class ViewHolder {
        ImageView mIV;
        TextView mTV;
    }
}
