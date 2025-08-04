REM @echo off
REM ============================================================================
REM タイトル：Voucher管理データ処理日付付加
REM 説明    ：Voucher管理データ処理日付付加。
REM version 1.00 2017/09/13 X.Liu:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB261020
IF %ERRORLEVEL% NEQ 0 goto error
:end

CD %BATCHF_HOME%
exit 0
:error
exit 1
