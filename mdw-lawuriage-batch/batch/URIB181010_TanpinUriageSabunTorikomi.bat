REM @echo off
REM ============================================================================
REM タイトル	：単品売上差分取込処理
REM 説明	：「リカバリ用新単品サマリ」ファイルを「新単品サマリ差分ワーク」へ挿入する。
REM version 1.00 2009/07/07 HX.Su:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== 単品売上差分取込処理
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB181010
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1