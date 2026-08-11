package com.hwa.excelllll.excelsolib;

import android.util.Log;
import android.view.View;

public class ConfigOptions {
    private static ConfigOptions instance;
    public OnViewClickListener customViewListener;
    private String tag = "ConfigOptions";
    private boolean isConfigValid = true;
    private boolean editingEnabled = true;
    private boolean saveAsEnabled = true;
    private boolean saveAsPdfEnabled = true;
    private boolean openInEnabled = true;
    private boolean openPdfInEnabled = true;
    private boolean shareEnabled = true;
    private boolean extClipboardInEnabled = true;
    private boolean extClipboardOutEnabled = true;
    private boolean imageInsertEnabled = true;
    private boolean photoInsertEnabled = true;
    private boolean printingEnabled = true;
    private boolean securePrintingEnabled = false;
    private boolean launchUrlEnabled = true;
    private boolean usePersistentFileState = true;
    private boolean allowAutoOpen = true;
    private boolean docAuthEntryEnabled = true;
    private boolean appAuthEnabled = false;
    private int appAuthTimeout = 30;
    private boolean saveEnabled = true;
    private boolean customSaveEnabled = false;
    private boolean trackChangesFeatureEnabled = false;
    private boolean formFillingEnabled = false;
    private boolean formSigningFeatureEnabled = false;
    private boolean redactionsEnabled = false;
    private boolean fullscreenEnabled = false;

    public interface OnViewClickListener {
        void onClick(View view);
    }

    private ConfigOptions() {
    }

    public boolean isFullscreenEnabled() {
        return this.fullscreenEnabled;
    }

    public void setAppAuthTimeout(int timeoutSeconds) {
        this.appAuthTimeout = timeoutSeconds;
        Log.i(this.tag, "mAppAuthTimeout set to " + String.valueOf(this.appAuthTimeout));
    }

    public void setSaveAsEnabled(boolean enabled) {
        this.saveAsEnabled = enabled;
        String logTag = this.tag;
        android.util.Log.d("ExcelConfig", "mSaveAsEnabled set to " + logTag);
    }

    public void setSaveAsPdfEnabled(boolean enabled) {
        this.saveAsPdfEnabled = enabled;
        String logTag = this.tag;
        android.util.Log.d("ExcelConfig", "mSaveAsPdfEnabled set to " + logTag);
    }

    public void setOpenInEnabled(boolean enabled) {
        this.openInEnabled = enabled;
        String logTag = this.tag;
        android.util.Log.d("ExcelConfig", "mOpenInEnabled set to " + logTag);
    }

    public void setOpenPdfInEnabled(boolean enabled) {
        this.openPdfInEnabled = enabled;
        String logTag = this.tag;
        android.util.Log.d("ExcelConfig", "OpenPdfInEnabled set to " + logTag);
    }

    public void setShareEnabled(boolean enabled) {
        this.shareEnabled = enabled;
        String logTag = this.tag;
        android.util.Log.d("ExcelConfig", "mShareEnabled set to " + logTag);
    }

    public void setExternalClipboardInEnabled(boolean enabled) {
        this.extClipboardInEnabled = enabled;
        String logTag = this.tag;
        android.util.Log.d("ExcelConfig", "mExtClipboardInEnabled set to " + logTag);
    }

    public void setExternalClipboardOutEnabled(boolean enabled) {
        this.extClipboardOutEnabled = enabled;
        String logTag = this.tag;
        android.util.Log.d("ExcelConfig", "mExtClipboardOutEnabled set to " + logTag);
    }

    public void setImageInsertEnabled(boolean enabled) {
        this.imageInsertEnabled = enabled;
        String logTag = this.tag;
        android.util.Log.d("ExcelConfig", "mImageInsertEnabled set to " + logTag);
    }

    public void setPhotoInsertEnabled(boolean enabled) {
        this.photoInsertEnabled = enabled;
        String logTag = this.tag;
        android.util.Log.d("ExcelConfig", "mPhotoInsertEnabled set to " + logTag);
    }

    public void setPrintingEnabled(boolean enabled) {
        this.printingEnabled = enabled;
        String logTag = this.tag;
        android.util.Log.d("ExcelConfig", "mPrintingEnabled set to " + logTag);
    }

    public void setSecurePrintingEnabled(boolean enabled) {
        this.securePrintingEnabled = enabled;
        String logTag = this.tag;
        android.util.Log.d("ExcelConfig", "mSecurePrintingEnabled set to " + logTag);
    }

    public void setLaunchUrlEnabled(boolean enabled) {
        this.launchUrlEnabled = enabled;
        String logTag = this.tag;
        android.util.Log.d("ExcelConfig", "mLaunchUrlEnabled set to " + logTag);
    }

    public void setSaveEnabled(boolean enabled) {
        this.saveEnabled = enabled;
        String logTag = this.tag;
        android.util.Log.d("ExcelConfig", "mSaveEnabled set to " + logTag);
    }

    public void setCustomSaveEnabled(boolean enabled) {
        this.customSaveEnabled = enabled;
        String logTag = this.tag;
        android.util.Log.d("ExcelConfig", "mSaveEnabled set to " + logTag);
    }

    public void setUsePersistentFileState(boolean enabled) {
        this.usePersistentFileState = enabled;
        String logTag = this.tag;
        android.util.Log.d("ExcelConfig", "mUsePersistentFileState set to " + logTag);
    }

    public void setAllowAutoOpen(boolean enabled) {
        this.allowAutoOpen = enabled;
        String logTag = this.tag;
        android.util.Log.d("ExcelConfig", "mAllowAutoOpen set to " + logTag);
    }

    public void setDocumentAuthEntryEnabled(boolean enabled) {
        this.docAuthEntryEnabled = enabled;
        String logTag = this.tag;
        android.util.Log.d("ExcelConfig", "mDocAuthEntryEnabled set to " + logTag);
    }

    public void setAppAuthEnabled(boolean enabled) {
        this.appAuthEnabled = enabled;
        String logTag = this.tag;
        android.util.Log.d("ExcelConfig", "mAppAuthEnabled set to " + logTag);
    }

    public void setTrackChangesFeatureEnabled(boolean enabled) {
        this.trackChangesFeatureEnabled = enabled;
        String logTag = this.tag;
        android.util.Log.d("ExcelConfig", "mTrackChangesFeatureEnabled set to " + logTag);
    }

    public void setFormFillingEnabled(boolean enabled) {
        this.formFillingEnabled = enabled;
        String logTag = this.tag;
        android.util.Log.d("ExcelConfig", "mFormFillingEnabled set to " + logTag);
    }

    public void setFormSigningFeatureEnabled(boolean enabled) {
        this.formSigningFeatureEnabled = enabled;
        String logTag = this.tag;
        android.util.Log.d("ExcelConfig", "mFormSigningFeatureEnabled set to " + logTag);
    }

    public void setRedactionsEnabled(boolean enabled) {
        this.redactionsEnabled = enabled;
        String logTag = this.tag;
        android.util.Log.d("ExcelConfig", "mRedactionsEnabled set to " + logTag);
    }

    public void setFullscreenEnabled(boolean enabled) {
        this.fullscreenEnabled = enabled;
        String logTag = this.tag;
        android.util.Log.d("ExcelConfig", "mFullscreenEnabled set to " + logTag);
    }

    public boolean isFormSigningFeatureEnabled() {
        return this.formSigningFeatureEnabled;
    }

    public boolean isRedactionsEnabled() {
        return this.redactionsEnabled;
    }

    public boolean isConfigValid() {
        return this.isConfigValid;
    }

    public boolean isEditingEnabled() {
        return this.editingEnabled;
    }

    public boolean isSaveAsEnabled() {
        return this.saveAsEnabled;
    }

    public boolean isSaveAsPdfEnabled() {
        return this.saveAsPdfEnabled;
    }

    public boolean isOpenInEnabled() {
        return this.openInEnabled;
    }

    public boolean isOpenPdfInEnabled() {
        return this.openPdfInEnabled;
    }

    public boolean isShareEnabled() {
        return this.shareEnabled;
    }

    public boolean isExternalClipboardInEnabled() {
        return this.extClipboardInEnabled;
    }

    public boolean isExternalClipboardOutEnabled() {
        return this.extClipboardOutEnabled;
    }

    public boolean isImageInsertEnabled() {
        return this.imageInsertEnabled;
    }

    public boolean isPhotoInsertEnabled() {
        return this.photoInsertEnabled;
    }

    public boolean isPrintingEnabled() {
        return this.printingEnabled;
    }

    public boolean isSecurePrintingEnabled() {
        return this.securePrintingEnabled;
    }

    public boolean isLaunchUrlEnabled() {
        return this.launchUrlEnabled;
    }

    public boolean isSaveEnabled() {
        return this.saveEnabled;
    }

    public boolean isCustomSaveEnabled() {
        return this.customSaveEnabled;
    }

    public boolean usesPersistentFileState() {
        return this.usePersistentFileState;
    }

    public boolean allowsAutoOpen() {
        return this.allowAutoOpen;
    }

    public boolean isDocumentAuthEntryEnabled() {
        return this.docAuthEntryEnabled;
    }

    public boolean isAppAuthEnabled() {
        return this.appAuthEnabled;
    }

    public int getAppAuthTimeout() {
        return this.appAuthTimeout;
    }

    public boolean isTrackChangesFeatureEnabled() {
        return this.trackChangesFeatureEnabled;
    }

    public boolean isFormFillingEnabled() {
        return this.formFillingEnabled;
    }

    public static ConfigOptions getInstance() {
        if (instance == null) {
            synchronized (ConfigOptions.class) {
                try {
                    instance = new ConfigOptions();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return instance;
    }

    public void setEditingEnabled(boolean enabled) {
        this.editingEnabled = enabled;
        String logTag = this.tag;
        android.util.Log.d("ExcelConfig", "mEditingEnabled set to " + logTag);
    }
}
