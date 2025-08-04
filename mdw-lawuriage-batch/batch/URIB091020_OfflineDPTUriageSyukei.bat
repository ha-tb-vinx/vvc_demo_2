REM @echo off
REM ============================================================================
REM タイトル	：オフラインDPT売上集計処理
REM 説明	：「オフラインDPT精算売上ワーク」からデータを抽出し「店別DPT売上データ」を作成する。
REM 		  その際、「オフラインDPT精算売上ワーク」を「DPT精算売上(仕入用)ワーク」にINSする。
REM version 1.00 2009/07/07 Y.Taniwaki:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== オフラインDPT売上集計処理
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB091020
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1