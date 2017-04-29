package com.example.a29149.yuyuan.Util.Annotation;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by 29149 on 2017/3/6.
 */

public class AnnotationUtil {
    public static void injectViews(Activity activity)
    {
        Class<? extends Activity> object = activity.getClass();
        Field[] fields = object.getDeclaredFields();
        for (Field field : fields) {

            ViewInject viewInject = field.getAnnotation(ViewInject.class);
            if (viewInject != null) {
                int viewId = viewInject.value();
                if (viewId != -1) {
                    try {
                        Method method = object.getMethod("findViewById", int.class);
                        Object resView = method.invoke(activity, viewId);
                        field.setAccessible(true);

                        field.set(activity, resView);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public static void injectViews(Fragment fragment,View view)
    {
        Class<? extends Fragment> object = fragment.getClass();
        Field[] fields = object.getDeclaredFields();
        for (Field field : fields) {

            ViewInject viewInject = field.getAnnotation(ViewInject.class);
            if (viewInject != null) {
                int viewId = viewInject.value();
                if (viewId != -1) {
                    try {

                        Method method = view.getClass().getMethod("findViewById", int.class);
                        Object resView = method.invoke(view, viewId);
                        field.setAccessible(true);

                        field.set(fragment, resView);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public static void setClickListener(final Activity activity)
    {
        Class<? extends Activity> object = activity.getClass();
        Method[] methods=object.getDeclaredMethods();
        for (Method method : methods)
        {
            final Method click=method;
            OnClick onClick = click.getAnnotation(OnClick.class);
            if (onClick != null)
            {
                int[] viewId=onClick.value();
                for (int i=0; i<viewId.length; i++)
                {
                    View view=activity.findViewById(viewId[i]);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try
                            {
                                click.invoke(activity, v);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

            OnItemClick onItemClick = click.getAnnotation(OnItemClick.class);
            if (onItemClick != null)
            {
                int[] viewId=onItemClick.value();
                for (int i=0; i<viewId.length; i++)
                {
                    ListView listView=(ListView)activity.findViewById(viewId[i]);
                    if (listView!=null)
                    {
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                try
                                {
                                    click.invoke(activity, view, position, id);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            }
        }
    }

    public static void setClickListener(final Fragment fragment,View container)
    {
        Class<? extends Fragment> object = fragment.getClass();
        Method[] methods=object.getDeclaredMethods();
        for (Method method : methods)
        {
            final Method click=method;
            OnClick onClick = click.getAnnotation(OnClick.class);
            if (onClick != null)
            {
                int[] viewId=onClick.value();
                for (int i=0; i<viewId.length; i++)
                {
                    try {

                        Method method1=container.getClass().getMethod("findViewById",int.class);
                        Object resView = method1.invoke(container, viewId[i]);
                        View view=(View)resView;
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try
                                {
                                    click.invoke(fragment, v);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }

            OnItemClick onItemClick = click.getAnnotation(OnItemClick.class);
            if (onItemClick != null)
            {
                int[] viewId=onItemClick.value();
                for (int i=0; i<viewId.length; i++)
                {
                    try {

                        Method method1=container.getClass().getMethod("findViewById",int.class);
                        Object resView = method1.invoke(container, viewId[i]);
                        ListView listView=(ListView)resView;
                        if (listView!=null)
                        {
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    try
                                    {
                                        click.invoke(fragment, view, position, id);
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    } catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

