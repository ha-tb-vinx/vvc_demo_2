REM @echo off
REM ============================================================================
REM タイトル：Eレコード営業日付加
REM 説明    ：POSジャーナルEレコードへBレコードの営業日を付加
REM version 1.00 2017/03/09 J.Endo:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB011210
IF %ERRORLEVEL% NEQ 0 goto error
:end

CD %BATCHF_HOME%
exit 0
:error
exit 1
