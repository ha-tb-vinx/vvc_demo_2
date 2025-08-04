REM @echo off
REM ============================================================================
REM タイトル	：(リカバリ)IF新単品サマリファイル作成処理
REM 説明	：IF用データを抽出し、「IF新単品サマリファイル（差分）」を作成する。
REM version 3.00 2014/02/27 M.Ayukawa [CUS00057] ランドローム様対応　IF新単品サマリファイル作成処理
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== (リカバリ)IF新単品サマリファイル作成処理
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB081050
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1