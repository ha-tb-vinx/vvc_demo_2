REM @echo off
REM ============================================================================
REM タイトル	：店別DPT打ち売上ワーク作成処理
REM 説明	：店別DPT打ち売上ワーク作成処理を実行する。
REM version 3.00 2013/10/18 T.Ooshiro:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== 店別DPT打ち売上ワーク作成処理
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB062070
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1
