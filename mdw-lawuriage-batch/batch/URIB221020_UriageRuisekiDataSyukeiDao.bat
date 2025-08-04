REM @echo off
REM ============================================================================
REM タイトル	：売上累積データ集計処理
REM 説明	：売上累積ワークのデータを集計し売上累積データに登録する。
REM version 1.00 2011/09/15 T.Kuzuhara:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== 売上累積データ集計処理
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB221020
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1