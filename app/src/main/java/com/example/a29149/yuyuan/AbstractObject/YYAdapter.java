package com.example.a29149.yuyuan.AbstractObject;

import java.util.List;

/**
 *
 * Author:       geyao
 * Date:         2017/6/23
 * Email:        gy2016@mail.ustc.edu.cn
 * Description:  适配器的接口，所有的适配器都应当实现它
 * Created by geyao on 2017/6/23.
 */

public interface YYAdapter<T extends AbstractDTO> {

    /**
     *
     * Author:       geyao
     * Date:         2017/6/23
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  获取当前数据链表的条数
     * @return
     */
    int getCount();

    /**
     *
     * Author:       geyao
     * Date:         2017/6/23
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  获取指定位置的数据，相对于数据链表来说的
     * @param position
     * @return
     */
    T getItem(int position);

    /**
     *
     * Author:       geyao
     * Date:         2017/6/23
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  往数据源中添加数据
     * @param data
     * @return
     */
    boolean addData(T data);

    /**
     *
     * Author:       geyao
     * Date:         2017/6/23
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  添加新数据列表
     * @param newData
     * @return
     */
    boolean addDataLst(List<T> newData);

    /**
     *
     * Author:       geyao
     * Date:         2017/6/23
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  往数据源添加新数据
     * @param newData 新数据表
     * @param head   标志位，如果为true，则头插
     * @return
     */
    boolean addExtinctDataList(List<T> newData, boolean head);

    /**
     *
     * Author:       geyao
     * Date:         2017/6/23
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  往指定位置添加数据
     * @param position
     * @param data
     * @return
     */
    boolean addData(int position, T data);

    /**
     *
     * Author:       geyao
     * Date:         2017/6/23 下午2:33
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  删除指定位置的元素元素，并将删除的元素返回
     * @param position 要删除的位置
     * @return
     */
    T remove(int position);

    /**
     *
     * Author:       geyao
     * Date:         2017/6/23 下午2:48
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  删除某个元素
     * @param data
     * @return
     */
    boolean remove(T data);

    /**
     *
     * Author:       geyao
     * Date:         2017/6/23 下午2:34
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  清空数据源表
     *
     */
    void clearData();


    /**
     *
     * Author:       geyao
     * Date:         2017/6/23 下午2:35
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  设置数据源表
     *
     * @param data
     */
    void setData(List<T> data);

    /**
     *
     * Author:       geyao
     * Date:         2017/6/23 下午2:35
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  获取数据源
     *
     * @return
     */
    List<T> getDataList();

    /**
     *
     * Author:       geyao
     * Date:         2017/6/23 下午2:36
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  更新数据源，需要重写equals方法
     * @param newData 更新后的新数据
     */
    void updateData(T newData);

    /**
     *
     * Author:       geyao
     * Date:         2017/6/23 下午2:46
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  通知数据更新
     *
     */
    void update();
}
