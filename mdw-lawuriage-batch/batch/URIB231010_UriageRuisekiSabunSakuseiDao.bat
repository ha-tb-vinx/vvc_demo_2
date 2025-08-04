REM @echo off
REM ============================================================================
REM タイトル	：売上累積差分作成処理
REM 説明	：売上累積差分データを作成する。
REM version 1.00 2011/09/15 T.Urano:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== 売上累積差分データ移送処理
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB231010
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1