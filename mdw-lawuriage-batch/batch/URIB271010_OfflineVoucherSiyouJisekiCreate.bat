REM @echo off
REM ============================================================================
REM タイトル：オフラインVoucher使用実績作成
REM 説明    ：Voucher使用実績データからオフラインVoucher使用実績を作成する。
REM version 1.00 2016/10/31 J.Endo:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB271010
IF %ERRORLEVEL% NEQ 0 goto error
:end

CD %BATCHF_HOME%
exit 0
:error
exit 1
