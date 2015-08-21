package apps.makarov.com.whereismycurrency.view.adapters;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import apps.makarov.com.whereismycurrency.models.ResultOperation;
import apps.makarov.com.whereismycurrency.view.adapters.sections.SectionsAdapter;
import apps.makarov.com.whereismycurrency.view.adapters.sections.holder.ModelWrapper;
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

    public HistoryAdapter(List<ResultOperation> resultOperations, OnClickToPresenter presenter) {
        super(getHolderWrappers(resultOperations, presenter));
        mPresenter = presenter;
    }

    private static List<ModelWrapper> getHolderWrappers(List<ResultOperation> historyItems, OnClickToPresenter onClickPresenter) {
        List<ResultOperation> mHistoryItems = ResultOperation.findHistoryOperation(historyItems);
        List<ResultOperation> mActiveItems = ResultOperation.findActiveOperation(historyItems);

        List<ModelWrapper> modelWrapperList = new ArrayList<>();

        if (mActiveItems.size() > 0)
            modelWrapperList.add(new ActiveSectionModel("3 august 2015"));

        for (ResultOperation operation : mActiveItems) {
            modelWrapperList.add(new ActiveItemModel(operation));
        }

        if (mHistoryItems.size() > 0)
            modelWrapperList.add(new InactiveSectionModel());

        for (ResultOperation operation : mHistoryItems) {
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
                    mPresenter.onClick((ResultOperation) object);
                }
            });
    }

    public static class ActiveItemModel implements ModelWrapper<ResultOperation> {

        private final ResultOperation mOperation;

        public ActiveItemModel(ResultOperation operation) {
            mOperation = operation;
        }

        @Override
        public ResultOperation getModel() {
            return mOperation;
        }

        @Override
        public int getType() {
            return ACTIVE_TYPE;
        }
    }

    public static class InactiveItemModel implements ModelWrapper<ResultOperation> {

        private final ResultOperation mOperation;

        public InactiveItemModel(ResultOperation operation) {
            mOperation = operation;
        }

        @Override
        public ResultOperation getModel() {
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
        void onClick(ResultOperation operation);
    }


}
