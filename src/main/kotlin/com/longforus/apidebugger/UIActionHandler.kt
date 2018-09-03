package com.longforus.apidebugger

import com.longforus.apidebugger.bean.ApiBean
import javax.swing.DefaultComboBoxModel
import javax.swing.JTable

/**
 * Created by XQ Yang on 8/31/2018  11:21 AM.
 * Description :
 */
object UIActionHandler {


    fun onSaveBaseUrl(selectedItem: Any) {
        MyValueHandler.curProject?.let {
            if (!it.baseUrlList.contains(selectedItem as String)) {
                it.baseUrlList.add(0, selectedItem)
                MyValueHandler.curBaseUrl = selectedItem
                OB.projectBox.put(it)
                val model = mainPanel.cbBaseUrl.model as DefaultComboBoxModel
                model.insertElementAt(selectedItem, 0)
            }
        }

    }

    fun onSaveApi(selectedItem: Any) {
        MyValueHandler.curProject?.let {
            val apiBean: ApiBean
            if (selectedItem is String) {
                apiBean = ApiBean(selectedItem, it.id)
                apiBean.encryptType = mainPanel.selectedEncryptID
                apiBean.method = mainPanel.selectedMethodType
                apiBean.parameMap = getParameMap(mainPanel.tbParame)
                it.apis.add(0, apiBean)
                val model = mainPanel.cbApiUrl.model as DefaultComboBoxModel
                model.insertElementAt(apiBean, 0)
                MyValueHandler.curApi = apiBean
            } else {
                apiBean = selectedItem as ApiBean
                apiBean.encryptType = mainPanel.selectedEncryptID
                apiBean.method = mainPanel.selectedMethodType
                apiBean.parameMap = getParameMap(mainPanel.tbParame)
            }
            OB.apiBox.put(apiBean)
        }
    }

    fun getParameMap(jTable: JTable): Map<String, String> {
        val map = HashMap<String, String>()
        for (i in 0..jTable.rowCount) {
            if (jTable.getValueAt(i, 0) == null) {
                break
            }
            map[jTable.getValueAt(i, 0) as String] = jTable.getValueAt(i, 1) as String
        }
        return map
    }

    fun onNewApi() {

    }

    fun onSend() {

    }

    fun onDelBaseUrl(selectedItem: Any) {
        MyValueHandler.curProject?.baseUrlList?.remove(selectedItem)
        mainPanel.cbBaseUrl.removeItem(selectedItem)
        OB.projectBox.put(MyValueHandler.curProject)
    }

    fun onDelApiUrl(selectedItem: ApiBean) {
        MyValueHandler.curProject?.apis?.remove(selectedItem)
        mainPanel.cbApiUrl.removeItem(selectedItem)
        MyValueHandler.curApi = MyValueHandler.curProject?.apis?.get(0)
        OB.apiBox.remove(selectedItem)
    }

    fun onApiItemChanged(item: ApiBean) {
        MyValueHandler.curApi = item
    }
}