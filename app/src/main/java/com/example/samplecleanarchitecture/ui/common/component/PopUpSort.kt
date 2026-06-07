package com.example.samplecleanarchitecture.ui.common.component

import android.content.Context
import android.transition.Transition
import com.example.samplecleanarchitecture.base.BasePopupWindow
import com.example.samplecleanarchitecture.component.setOnSingleListener
import com.example.samplecleanarchitecture.databinding.LayoutMenuSortBinding
import com.example.samplecleanarchitecture.utils.invisible
import com.example.samplecleanarchitecture.utils.visible

class PopUpSort(
    context: Context, private val callBack: CallBack
) : BasePopupWindow<LayoutMenuSortBinding>(context, LayoutMenuSortBinding::inflate) {

    init {
        setUpUi()
    }

    private fun setUpUi() {
        binding.apply {
            btnSortByDate.setOnSingleListener {
                ivCheck1.visible()
                ivCheck2.invisible()
                ivCheck3.invisible()
                callBack.onClick(TypeSort.BY_DATE)
            }
            btnSortByCommonName.setOnSingleListener {
                ivCheck2.visible()
                ivCheck1.invisible()
                ivCheck3.invisible()
                callBack.onClick(TypeSort.BY_COMMON_NAME)
            }
            btnSortScienceName.setOnSingleListener {
                ivCheck3.visible()
                ivCheck1.invisible()
                ivCheck2.invisible()
                callBack.onClick(TypeSort.BY_SCIENCE_NAME)
            }
        }
    }

    interface CallBack {
        fun onClick(typeSort: TypeSort)
    }

    enum class TypeSort {
        BY_DATE, BY_COMMON_NAME, BY_SCIENCE_NAME
    }
    /*

    use :
    btnSort.setOnSingleListener { view ->
                val menuPopup = PopUpSort(this@MainActivity, object : PopUpSort.CallBack {
                    override fun onClick(typeSort: PopUpSort.TypeSort) {
                        log(typeSort.toString())
                    }
                })
                menuPopup.showAsDropDown(view, 0, 10)
            }
     */
}
