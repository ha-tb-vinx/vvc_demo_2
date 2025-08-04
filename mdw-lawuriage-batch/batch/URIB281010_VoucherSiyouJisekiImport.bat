REM @echo off
REM ============================================================================
REM タイトル：Voucher使用実績取込
REM 説明    ：ＣレコードＯＫワークからオンラインVoucherデータを取り込む。
REM version 1.00 2016/10/31 J.Endo:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB281010
IF %ERRORLEVEL% NEQ 0 goto error
:end

CD %BATCHF_HOME%
exit 0
:error
exit 1
