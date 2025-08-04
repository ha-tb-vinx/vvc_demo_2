REM @echo off
REM ============================================================================
REM タイトル	：コピーファイル処理
REM 説明	：コピー元フォルダのファイルを、コピー先フォルダへコピーする。
REM version 3.00 2013/10/24 S.Arakawa [CUS00057] ランドローム様　POSインターフェイス仕様変更対応　コピーファイル処理
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== コピーファイル処理
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB131075
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1