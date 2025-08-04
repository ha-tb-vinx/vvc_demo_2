REM @echo off
REM ============================================================================
REM タイトル	：新単品明細ログ差分ファイル作成処理
REM 説明	：「新単品サマリワーク」のデータを抽出し、「IF単品別売上データ」を作成する。
REM version 1.00 2009/07/07 Y.Taniwaki:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== 新単品明細ログ差分ファイル作成処理
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB171020
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1
