REM @echo off
REM ============================================================================
REM タイトル：売上INVOICE作成処理
REM 説明    ：卸出荷伝票ワークを作成する。
REM version 1.00 2017/04/04 X.Liu:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB501040
IF %ERRORLEVEL% NEQ 0 goto error
:end

CD %BATCHF_HOME%
exit 0
:error
exit 1
