REM @echo off
REM ============================================================================
REM タイトル	：売上累積取込処理
REM 説明	：売上累積データを取り込む。
REM version 1.00 2011/09/15 T.Kuzuhara:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== 売上累積取込処理
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB221010
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1