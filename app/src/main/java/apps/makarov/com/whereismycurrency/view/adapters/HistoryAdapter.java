package apps.makarov.com.whereismycurrency.view.adapters;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import apps.makarov.com.whereismycurrency.models.ResultOperationData;
import apps.makarov.com.whereismycurrency.view.adapters.sections_adapter.SectionsAdapter;
import apps.makarov.com.whereismycurrency.view.adapters.sections_adapter.holder.ModelWrapper;
import apps.makarov.com.whereismycurrency.view.adapters.viewholders.ActiveOperationSectionViewHolderWrapper;
import apps.makarov.com.whereismycurrency.view.adapters.viewholders.ActiveOperationViewHolderWrapper;
import apps.makarov.com.whereismycurrency.view.adapters.viewholders.InactiveOperationSectionViewHolderWrapper;
import apps.makarov.com.whereismycurrency.view.adapters.viewholders.InactiveOperationViewHolderWrapper;
import apps.makarov.com.whereismycurrency.view.adapters.viewholders.ViewHolderWrapper;

/**
 * Created by makarov on 30/06/15.
 */

/*
для создания нового типа элемента
1) реализовать новый тип враппера над моделью - ModelWrapper<T>
2) реализовать новый типа враппера над  холдером - ViewHolderWrapper<T>
3)
 */

public class HistoryAdapter extends SectionsAdapter<ViewHolderWrapper> {
    public static final String TAG = HistoryAdapter.class.getSimpleName();

    private static final int ACTIVE_TYPE = 0;
    private static final int INACTIVE_TYPE = 1;
    private static final int ACTIVE_SECTION_TYPE = 2;
    private static final int INACTIVE_SECTION_TYPE = 3;

    private OnClickToPresenter mPresenter;

    public HistoryAdapter(List<ResultOperationData> resultOperations, OnClickToPresenter presenter) {
        super(getHolderWrappers(resultOperations, presenter));
        mPresenter = presenter;
    }

    private static List<ModelWrapper> getHolderWrappers(List<ResultOperationData> historyItems, OnClickToPresenter onClickPresenter) {
        List<ResultOperationData> mHistoryItems = ResultOperationData.findHistoryOperation(historyItems);
        List<ResultOperationData> mActiveItems = ResultOperationData.findActiveOperation(historyItems);

        List<ModelWrapper> modelWrapperList = new ArrayList<>();

        if (mActiveItems.size() > 0)
            modelWrapperList.add(new ActiveSectionModel("3 august 2015"));

        for (ResultOperationData operation : mActiveItems) {
            modelWrapperList.add(new ActiveItemModel(operation));
        }

        if (mHistoryItems.size() > 0)
            modelWrapperList.add(new InactiveSectionModel());

        for (ResultOperationData operation : mHistoryItems) {
            modelWrapperList.add(new InactiveItemModel(operation));
        }

        return modelWrapperList;
    }

    @Override
    protected ViewHolderWrapper getHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ACTIVE_TYPE:
                return new ActiveOperationViewHolderWrapper(parent);
            case INACTIVE_TYPE:
                return new InactiveOperationViewHolderWrapper(parent);
            case ACTIVE_SECTION_TYPE:
                return new ActiveOperationSectionViewHolderWrapper(parent);
            case INACTIVE_SECTION_TYPE:
                return new InactiveOperationSectionViewHolderWrapper(parent);
        }
        return null;
    }

    @Override
    protected void createdListeners(ViewHolderWrapper holder, final Object object, int type) {
        if (type == ACTIVE_TYPE || type == INACTIVE_TYPE
                )
            holder.setOnClicklistener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.onClick((ResultOperationData) object);
                }
            });
    }

    public static class ActiveItemModel implements ModelWrapper<ResultOperationData> {

        private final ResultOperationData mOperation;

        public ActiveItemModel(ResultOperationData operation) {
            mOperation = operation;
        }

        @Override
        public ResultOperationData getModel() {
            return mOperation;
        }

        @Override
        public int getType() {
            return ACTIVE_TYPE;
        }
    }

    public static class InactiveItemModel implements ModelWrapper<ResultOperationData> {

        private final ResultOperationData mOperation;

        public InactiveItemModel(ResultOperationData operation) {
            mOperation = operation;
        }

        @Override
        public ResultOperationData getModel() {
            return mOperation;
        }

        @Override
        public int getType() {
            return INACTIVE_TYPE;
        }
    }

    public static class ActiveSectionModel implements ModelWrapper<String> {
        private final String mSubTitle;

        public ActiveSectionModel( String subTitle) {
            mSubTitle = subTitle;
        }

        @Override
        public String getModel() {
            return mSubTitle;
        }

        @Override
        public int getType() {
            return ACTIVE_SECTION_TYPE;
        }
    }

    public static class InactiveSectionModel implements ModelWrapper<Void> {


        public InactiveSectionModel() {
        }

        @Override
        public Void getModel() {
            return null;
        }

        @Override
        public int getType() {
            return INACTIVE_SECTION_TYPE;
        }
    }


    public interface OnClickToPresenter {
        void onClick(ResultOperationData operation);
    }


}
