REM @echo off
REM ============================================================================
REM タイトル	：店別DPT売上集計処理
REM 説明	：「DPT精算売上ワーク」データ抽出し、「店別DPT売上データ」と
REM 		  「DPT精算売上（仕入用）ワーク」に挿入する。
REM version 1.00 2009/07/07 Y.Taniwaki:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== 店別DPT売上集計処理
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB031020
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1
