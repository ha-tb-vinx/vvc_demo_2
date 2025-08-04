REM @echo off
REM ============================================================================
REM タイトル	：オフラインDPT売上精算差異データ作成処理
REM 説明	：「オフラインDPT精算売上ワーク」からデータを抽出し「オフラインDPT精算売上差異ワーク」を作成する。
REM 		   その際、「オフラインDPT精算売上累積ワーク」がなければINS、あればUPDする。
REM version 1.00 2009/12/25 M.Tada:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== オフラインDPT売上精算差異データ作成処理
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB091030
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1