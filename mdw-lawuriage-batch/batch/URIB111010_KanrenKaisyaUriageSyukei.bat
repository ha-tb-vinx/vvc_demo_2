REM @echo off
REM ============================================================================
REM タイトル	：関連会社売上集計処理
REM 説明	：関連会社テナントの売上データ営業情報へ渡す為の処理です。
REM version 1.00 2009/07/07 Y.Taniwaki:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== 関連会社売上集計処理
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB111010
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1