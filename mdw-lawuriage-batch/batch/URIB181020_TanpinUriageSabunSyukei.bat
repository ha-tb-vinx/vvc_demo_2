REM @echo off
REM ============================================================================
REM タイトル	：単品サマリ差分リカバリ処理
REM 説明	：単品サマリで差分データが発生した場合のリカバリを想定し、
REM               リカバリ用ワーク日々差分ＩＦデータを作成する。
REM version 1.00 2009/06/11 HX.Su:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== 単品売上差分集計処理
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB181020
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1