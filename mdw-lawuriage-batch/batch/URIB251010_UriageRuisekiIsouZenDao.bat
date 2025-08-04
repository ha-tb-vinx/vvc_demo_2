REM @echo off
REM ============================================================================
REM タイトル	：前日売上累積データ移送処理
REM 説明	：売上累積データを前日売上累積データに移送する。
REM version 1.00 2011/09/15 T.Kuzuhara:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== 前日売上累積データ移送処理
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB251010
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1